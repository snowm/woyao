package com.woyao.customer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import com.snowm.security.profile.domain.Gender;
import com.snowm.utils.query.PaginationBean;
import com.woyao.JsonUtils;
import com.woyao.customer.chat.ChatUtils;
import com.woyao.customer.chat.MessageCacheOperator;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.chat.BlockDTO;
import com.woyao.customer.dto.chat.InMsg;
import com.woyao.customer.dto.chat.Inbound;
import com.woyao.customer.dto.chat.MsgQueryRequest;
import com.woyao.customer.dto.chat.OutMsgDTO;
import com.woyao.customer.dto.chat.Outbound;
import com.woyao.customer.dto.chat.OutboundCommand;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IMobileService;
import com.woyao.customer.service.IProductService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.profile.ProfileWX;
import com.woyao.utils.PaginationUtils;

@Component("chatService")
public class ChatServiceImpl implements IChatService {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "sessionContainer")
	private SessionContainer sessionContainer;

	@Resource(name = "messageCacheOperator")
	private MessageCacheOperator messageCacheOperator;

	@Resource(name = "productService")
	private IProductService productService;

	@Resource(name = "mobileService")
	private IMobileService mobileService;

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(readOnly = true)
	@Override
	public String newChatter(WebSocketSession wsSession, HttpSession httpSession) {
		this.sessionContainer.wsEnabled(wsSession, httpSession);
		return null;
	}

	@Override
	public void leave(WebSocketSession wsSession) {
		this.sessionContainer.wsClosed(wsSession.getId());
	}

	@Override
	public void acceptMsg(WebSocketSession wsSession, Inbound inbound) {
		Lock lock = SessionUtils.getMsgCacheLock(wsSession);
		Map<Long, InMsg> cache = SessionUtils.getMsgCache(wsSession);
		InMsg inMsg = this.messageCacheOperator.receiveMsg(lock, cache, inbound);
		if (inMsg == null) {
			return;
		}
		// Received the whole message
		StringBuilder sb = new StringBuilder();
		for (BlockDTO block : inMsg.getBlocks()) {
			sb.append(block.getBlock());
		}
		try {
			InMsg tmpMsg = JsonUtils.toObject(sb.toString(), InMsg.class);
			inMsg.setText(tmpMsg.getText());

			String base64PicString = tmpMsg.getPic();
			if (!StringUtils.isBlank(base64PicString)) {
				String path = "/pic/" + ChatUtils.savePic(base64PicString);
				if (log.isDebugEnabled()) {
					log.debug("saved pic:" + path);
				}
				inMsg.setPic(path);
			}

			Long msgProductId = inMsg.getProductId();
			if (msgProductId != null) {
				MsgProductDTO msgProductDTO = this.productService.getMsgProduct(msgProductId);
				if (msgProductDTO == null) {

				}
			}
			Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
			ChatterDTO sender = SessionUtils.getChatter(wsSession);
			long id = this.saveMsg(inMsg, sender.getId(), chatRoomId);

			OutMsgDTO outMsg = new OutMsgDTO();
			outMsg.setId(id);
			outMsg.setSender(sender);
			outMsg.setText(inMsg.getText());
			outMsg.setPic(inMsg.getPic());
			outMsg.setCommand(OutboundCommand.SEND_MSG);
			outMsg.setDuration(0);
			outMsg.setPrivacy(inMsg.getTo() == null);
			this.sendOutMsg(outMsg, inMsg.getTo(), chatRoomId, wsSession);
		} catch (IOException e) {
			log.error("Process message failure!", e);
		}
	}

	private long saveMsg(InMsg msg, Long senderId, Long chatRoomId) {
		ChatMsg m = new ChatMsg();
		if (msg.getTo() == null) {
			m.setChatRoomId(chatRoomId);
		}
		m.setTo(msg.getTo());
		m.setText(msg.getText());
		m.setPicURL(msg.getPic());
		m.setFree(true);
		m.setFrom(senderId);
		m.setProductId(msg.getProductId());
		this.dao.save(m);
		return m.getId();
	}

	public void sendOutMsg(Outbound outbound, Long to, Long chatRoomId, WebSocketSession wsSession) {
		Set<WebSocketSession> targetSessions = null;
		if (to == null && chatRoomId != null) {
			targetSessions = this.getAllRoomChatterSessions(chatRoomId);
		} else {
			targetSessions = this.getTargetChatterSessions(to);
		}
		if (targetSessions == null) {
			targetSessions = new HashSet<>();
		}
		targetSessions.remove(wsSession);
		for (WebSocketSession session : targetSessions) {
			try {
				outbound.send(session);
			} catch (IOException e) {
				log.warn("Msg send fail to session:" + session.getId(), e);
			}
		}
		try {
			outbound.setCommand(OutboundCommand.SEND_MSG_ACK);
			outbound.send(wsSession);
		} catch (IOException e) {
			log.warn("Msg send fail to sender session:" + wsSession.getId(), e);
		}
	}

	@Override
	public ChatterDTO getChatter(long chatterId) {
		ChatterDTO dto = this.sessionContainer.getChatter(chatterId);
		if (dto == null) {
			dto = this.getChatterFromDB(chatterId);
		}
		return dto;
	}

	private ChatterDTO getChatterFromDB(long chatterId) {
		ProfileWX profile = this.dao.get(ProfileWX.class, chatterId);
		ChatterDTO dto = new ChatterDTO();
		BeanUtils.copyProperties(profile, dto);
		return dto;
	}

	@Override
	public PaginationBean<ChatterDTO> listOnlineChatters(long chatRoomId, Gender gender, long pageNumber, int pageSize) {
		Set<WebSocketSession> wsSessions = this.sessionContainer.getWsSessionOfRoom(chatRoomId);
		List<ChatterDTO> dtos = new ArrayList<>();
		if (wsSessions != null && !wsSessions.isEmpty()) {
			for (WebSocketSession wsSession : wsSessions) {
				ChatterDTO chatter = SessionUtils.getChatter(wsSession);
				if (gender == null || chatter.getGender() == gender) {
					dtos.add(chatter);
				}
			}
		}
		return PaginationUtils.paging(dtos, pageNumber, pageSize);
	}

	@Override
	public Set<WebSocketSession> getTargetChatterSessions(long chatterId) {
		return this.sessionContainer.getWsSessionOfChatter(chatterId);
	}

	@Override
	public Set<WebSocketSession> getAllRoomChatterSessions(long chatRoomId) {
		return this.sessionContainer.getWsSessionOfRoom(chatRoomId);
	}

	@Override
	public List<OutMsgDTO> listMsg(MsgQueryRequest request) {
		List<Criterion> criterions = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		if (request.getMaxId() != null) {
			criterions.add(Restrictions.lt("id", request.getMaxId()));
			orders.add(Order.desc("id"));
		} else if (request.getMinId() != null) {
			criterions.add(Restrictions.gt("id", request.getMinId()));
			orders.add(Order.asc("id"));
		}
		if (request.getWithChatterId() != null) {
			Criterion to = Restrictions.and(Restrictions.eq("from", request.getSelfChatterId()),
					Restrictions.eq("to", request.getWithChatterId()));
			Criterion from = Restrictions.and(Restrictions.eq("to", request.getSelfChatterId()),
					Restrictions.eq("from", request.getWithChatterId()));
			criterions.add(Restrictions.or(to, from));
		} else {
			ChatRoomDTO room = this.mobileService.getChatRoom(request.getShopId());
			criterions.add(Restrictions.eq("chatRoomId", room.getId()));
		}
		List<ChatMsg> result = this.dao.query(ChatMsg.class, criterions, orders, 1L, request.getPageSize());
		List<OutMsgDTO> dtos = new ArrayList<>();
		for (ChatMsg e : result) {
			OutMsgDTO dto = new OutMsgDTO();
			dto.setId(e.getId());
			dto.setPic(e.getPicURL());
			dto.setText(e.getText());
			ChatterDTO sender = this.getChatter(e.getFrom());
			dto.setSender(sender);
			if (!e.getFrom().equals(request.getSelfChatterId())) {
				dto.setCommand(OutboundCommand.SEND_MSG);
			} else {
				dto.setCommand(OutboundCommand.SEND_MSG_ACK);
			}
			dtos.add(dto);
		}

		return null;
	}
}
