package com.woyao.customer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
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
import com.woyao.customer.chat.UploadUtils;
import com.woyao.customer.chat.MessageCacheOperator;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.chat.BlockDTO;
import com.woyao.customer.dto.chat.ChatPicDTO;
import com.woyao.customer.dto.chat.ErrorOutbound;
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
import com.woyao.utils.JsonUtils;
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
		ChatterDTO sender = SessionUtils.getChatter(wsSession);
		if (sender.getId().equals(inMsg.getTo())) {
			ErrorOutbound error = new ErrorOutbound("不能给自己发消息！");
			try {
				error.send(wsSession);
			} catch (IOException e) {
				log.error("Send error message failure!", e);
			}
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
				String path = "/pic/" + UploadUtils.savePic(base64PicString);
				if (log.isDebugEnabled()) {
					log.debug("saved pic:" + path);
				}
				inMsg.setPic(path);
			}

			Long msgProductId = inMsg.getProductId();
			if (msgProductId != null) {
				MsgProductDTO msgProductDTO = this.productService.getMsgProduct(msgProductId);
				if (msgProductDTO != null) {

				}
			}
			Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
			long id = this.saveMsg(inMsg, sender.getId(), chatRoomId);

			OutMsgDTO outMsg = new OutMsgDTO();
			outMsg.setId(id);
			outMsg.setSender(sender);
			outMsg.setText(inMsg.getText());
			outMsg.setPic(inMsg.getPic());
			outMsg.setCommand(OutboundCommand.ACCEPT_MSG);
			outMsg.setDuration(0);
			outMsg.setPrivacy(inMsg.getTo() != null);
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

	@Override
	public void sendOutMsg(Outbound outbound, Long to, Long chatRoomId, WebSocketSession wsSession) {
		Set<WebSocketSession> targetSessions = null;
		// 群聊消息
		ErrorOutbound error = null;
		if (to == null && chatRoomId != null) {
			targetSessions = this.getAllRoomChatterSessions(chatRoomId);
			if (CollectionUtils.isEmpty(targetSessions)) {
				error = new ErrorOutbound("聊天室是空的！");
				log.debug("聊天室是空的！");
			}
		} else {
			targetSessions = this.getTargetChatterSessions(to);
			if (CollectionUtils.isEmpty(targetSessions)) {
				error = new ErrorOutbound("对方已经下线！");
			}
		}
		try {
			if (error != null) {
				error.send(wsSession);
				return;
			}
		} catch (IOException e) {
			log.error("Send error message failure!", e);
			return;
		}
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

	@Override
	public ChatterDTO getChatterFromDB(long chatterId) {
		ProfileWX profile = this.dao.get(ProfileWX.class, chatterId);
		ChatterDTO dto = new ChatterDTO();
		BeanUtils.copyProperties(profile, dto);
		return dto;
	}

	@Override
	public PaginationBean<ChatterDTO> listOnlineChatters(Long selfChatterId, long chatRoomId, Gender gender, long pageNumber,
			int pageSize) {
		Set<WebSocketSession> wsSessions = this.sessionContainer.getWsSessionOfRoom(chatRoomId);
		List<ChatterDTO> dtos = new ArrayList<>();
		if (wsSessions != null && !wsSessions.isEmpty()) {
			for (WebSocketSession wsSession : wsSessions) {
				ChatterDTO chatter = SessionUtils.getChatter(wsSession);
				if (gender == null || chatter.getGender() == gender) {
					if (chatter.getId().equals(selfChatterId)) {
						ChatterDTO tmpChatter = new ChatterDTO();
						BeanUtils.copyProperties(chatter, tmpChatter);
						dtos.add(tmpChatter);
					} else {
						dtos.add(chatter);
					}
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
	public List<OutMsgDTO> listHistoryMsg(MsgQueryRequest request) {
		// 如果max和min都为空，那么默认就设置最大的为-1，那么后面的取值就是order的降序
		if (request.getMaxId() == null && request.getMinId() == null) {
			request.setMaxId(-1L);
		}
		List<Criterion> criterions = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		Long maxId = request.getMaxId();
		if (maxId != null) {
			if (maxId > 0) {
				criterions.add(Restrictions.lt("id", maxId));
			}
			orders.add(Order.desc("id"));
		} else {
			Long minId = request.getMinId();
			if (minId > 0) {
				criterions.add(Restrictions.gt("id", minId));
			}
			orders.add(Order.asc("id"));
		}
		Long selfChatterId = request.getSelfChatterId();
		Long withChatterId = request.getWithChatterId();
		// 如果withChatterId不为空，表明要查询和该用户的私聊消息历史记录
		if (withChatterId != null) {
			Criterion to = Restrictions.and(Restrictions.eq("from", selfChatterId), Restrictions.eq("to", withChatterId));
			Criterion from = Restrictions.and(Restrictions.eq("to", selfChatterId), Restrictions.eq("from", withChatterId));
			criterions.add(Restrictions.or(to, from));
		} else {
			criterions.add(Restrictions.eq("chatRoomId", request.getChatRoomId()));
		}
		List<ChatMsg> result = this.dao.query(ChatMsg.class, criterions, orders, 1L, request.getPageSize());
		List<OutMsgDTO> dtos = new ArrayList<>();
		for (ChatMsg e : result) {
			OutMsgDTO dto = new OutMsgDTO();
			dto.setId(e.getId());
			dto.setPic(e.getPicURL());
			dto.setText(e.getText());
			ChatterDTO sender = this.getChatter(e.getFrom());
			// 如果没找到sender，忽略此条消息
			if (sender == null) {
				if (log.isDebugEnabled()) {
					log.debug(String.format("用户%s丢失！", e.getFrom()));
				}
				continue;
			}
			dto.setSender(sender);
			// 如果本聊天者不是消息的发送者，那么表明这是自己收到的别人发的消息，反之就是自己发出的消息
			if (!selfChatterId.equals(e.getFrom())) {
				dto.setCommand(OutboundCommand.ACCEPT_MSG);
			} else {
				// 自己发出的消息返回通知
				dto.setCommand(OutboundCommand.SEND_MSG_ACK);
			}
			// 如果本聊天者就是消息的发送目标，那么表明这是个别人发给自己的私聊消息
			if (selfChatterId.equals(e.getTo())) {
				dto.setPrivacy(true);
			}
			dtos.add(dto);
		}

		return dtos;
	}	
	@Override
	public List<ChatPicDTO> getPicUrl(Long id,Long pageNumber,Integer pageSize) {
		List<ChatPicDTO> dtos=new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fromId", id);	
		List<ChatMsg> chatMsgs=this.dao.query("from ChatMsg as c where c.from = :fromId order by modification.creationDate desc", paramMap, pageNumber, pageSize);
		for (ChatMsg chatMsg : chatMsgs) {
			ChatPicDTO dto=new ChatPicDTO();
			if(chatMsg.getTo()==null){
				if(chatMsg.getPicURL()!=null && !chatMsg.getPicURL().isEmpty()){
					dto.setPicUrl(chatMsg.getPicURL());
				}				
			}
			dtos.add(dto);
		}
		return dtos;
	}
}
