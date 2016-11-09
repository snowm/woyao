package com.woyao.customer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import com.snowm.security.profile.domain.Gender;
import com.snowm.utils.query.PaginationBean;
import com.woyao.cache.RicherReportCache;
import com.woyao.customer.chat.MessageCacheOperator;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.chat.session.SessionContainer;
import com.woyao.customer.disruptor.ChatMsgEventProducer;
import com.woyao.customer.disruptor.LongEventProducer;
import com.woyao.customer.dto.ChatPicDTO;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ChatRoomStatistics;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.dto.RicherDTO;
import com.woyao.customer.dto.chat.BlockDTO;
import com.woyao.customer.dto.chat.MsgQueryRequest;
import com.woyao.customer.dto.chat.in.EntireInMsg;
import com.woyao.customer.dto.chat.out.ChatRoomInfoDTO;
import com.woyao.customer.dto.chat.out.ErrorOutbound;
import com.woyao.customer.dto.chat.out.OutMsgDTO;
import com.woyao.customer.dto.chat.out.Outbound;
import com.woyao.customer.dto.chat.out.OutboundCommand;
import com.woyao.customer.dto.chat.out.SelfChatterInfoDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IMobileService;
import com.woyao.customer.service.IOrderService;
import com.woyao.customer.service.IProductService;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.service.UploadService;
import com.woyao.utils.JsonUtils;
import com.woyao.utils.PaginationUtils;

@Component("chatService")
public class ChatServiceImpl implements IChatService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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

	@Resource(name = "uploadService")
	private UploadService uploadService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "submitOrderProducer")
	@Lazy(true)
	private LongEventProducer submitOrderProducer;

	@Resource(name = "chatMsgEventProducer")
	private ChatMsgEventProducer chatMsgEventProducer;

	@Resource(name = "profileWxService")
	private IProfileWxService profileWxService;

	@Resource(name = "richerReportCache")
	private RicherReportCache richerReportCache;

	@Transactional(readOnly = true)
	@Override
	public void newChatter(WebSocketSession wsSession) {
		this.sessionContainer.wsEnabled(wsSession);
		sendSelfInfo(wsSession);
		Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
		if (chatRoomId != null) {
			boardRoomStatisticsInfo(chatRoomId);
		}
	}

	private void sendSelfInfo(WebSocketSession wsSession) {
		SelfChatterInfoDTO selfDTO = new SelfChatterInfoDTO();
		ProfileDTO self = SessionUtils.getChatter(wsSession);
		selfDTO.setSelf(self);
		this.sendMsg(selfDTO, wsSession);
	}

	@Override
	public void leave(WebSocketSession wsSession) {
		this.sessionContainer.wsClosed(wsSession);
		Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
		if (chatRoomId != null) {
			boardRoomStatisticsInfo(chatRoomId);
		}
	}

	private void boardRoomStatisticsInfo(long chatRoomId) {
		Set<WebSocketSession> sessions = this.sessionContainer.getWsSessionOfRoom(chatRoomId);
		ChatRoomStatistics statistics = this.sessionContainer.getRoomStatistics(chatRoomId);
		Outbound outbound = new ChatRoomInfoDTO(statistics);
		for (WebSocketSession s : sessions) {
			this.sendMsg(outbound, s);
		}
	}

	@Override
	public void acceptMsg(WebSocketSession wsSession, EntireInMsg inMsg) {
		ProfileDTO sender = SessionUtils.getChatter(wsSession);
		// Received the whole message
		StringBuilder sb = new StringBuilder();
		for (BlockDTO block : inMsg.getBlocks()) {
			sb.append(block.getBlock());
		}
		try {
			EntireInMsg tmpMsg = JsonUtils.toObject(sb.toString(), EntireInMsg.class);
			inMsg.setText(tmpMsg.getText());

			String base64PicString = tmpMsg.getPic();
			if (!StringUtils.isBlank(base64PicString)) {
				UploadService.FileInfo fileInfo = uploadService.savePic(base64PicString);
				inMsg.setPic(fileInfo.getUrl());
			}

			Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
			ChatMsg savedMsg = this.saveMsg(inMsg, sender.getId(), chatRoomId);

			Long msgProductId = inMsg.getProductId();
			if (msgProductId != null) {
				Long shopId = SessionUtils.getShopId(wsSession);
				MsgProductDTO msgProductDTO = this.productService.getMsgProduct(shopId, msgProductId);
				if (msgProductDTO != null) {
					OrderDTO savedOrder = orderService.placeOrder(shopId, savedMsg);
					Long orderId = savedOrder.getId();
					this.submitOrderProducer.produce(orderId);
					return;
				}
			}
			long msgId = savedMsg.getId();

			OutMsgDTO outbound = generateOutMsg(inMsg, sender, msgId);
			if (inMsg.getTo() != null) {
				this.sendPrivacyMsg(outbound, inMsg.getTo(), wsSession);
			} else {
				this.sendRoomMsg(outbound, chatRoomId, wsSession);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private OutMsgDTO generateOutMsg(EntireInMsg inMsg, ProfileDTO sender, long id) {
		OutMsgDTO outMsg = new OutMsgDTO();
		outMsg.setId(id);
		outMsg.setClientMsgId(inMsg.getMsgId());
		outMsg.setSender(sender);
		outMsg.setText(inMsg.getText());
		outMsg.setPic(inMsg.getPic());
		outMsg.setCommand(OutboundCommand.ACCEPT_MSG);
		outMsg.setDuration(0);
		outMsg.setPrivacy(inMsg.getTo() != null);
		outMsg.setCreationDate(new Date());
		return outMsg;
	}

	private ChatMsg saveMsg(EntireInMsg msg, Long senderId, Long chatRoomId) {
		ChatMsg m = new ChatMsg();
		m.setChatRoomId(chatRoomId);
		m.setTo(msg.getTo());
		m.setText(msg.getText());
		m.setPicURL(msg.getPic());
		m.setFree(true);
		m.setPayed(false);
		m.setRemoteAddr(msg.getRemoteAddress());
		m.setFrom(senderId);
		m.setProductId(msg.getProductId());
		m.setClientMsgId(msg.getMsgId());
		// if (msg.getProductId() != null) {
		// m.setFree(false);
		// m.setPayed(false);
		// }
		this.dao.save(m);
		return m;
	}

	@Override
	public ProfileDTO getChatter(long chatterId) {
		ProfileDTO dto = this.sessionContainer.getChatter(chatterId);
		if (dto == null) {
			dto = this.profileWxService.getById(chatterId);
		}
		return dto;
	}

	@Override
	public PaginationBean<ProfileDTO> listOnlineChatters(Long selfChatterId, long chatRoomId, Gender gender, long pageNumber,
			int pageSize) {
		Set<WebSocketSession> wsSessions = this.sessionContainer.getChatterWsSessionOfRoom(chatRoomId);
		List<ProfileDTO> dtos = new ArrayList<>();
		if (wsSessions != null && !wsSessions.isEmpty()) {
			for (WebSocketSession wsSession : wsSessions) {
				ProfileDTO chatter = SessionUtils.getChatter(wsSession);
				if (gender == null || chatter.getGender() == gender) {
					if (chatter.getId().equals(selfChatterId)) {
						ProfileDTO tmpChatter = new ProfileDTO();
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
		// 如果max和min都为空，那么默认就设置最大的为-1，那么后面的取值就是id的降序
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
		Criterion criterionFree = Restrictions.eqOrIsNull("free", true);
		Criterion criterionPayed = Restrictions.eqOrIsNull("payed", true);
		criterions.add(Restrictions.or(criterionFree, criterionPayed));
		List<ChatMsg> result = this.dao.query(ChatMsg.class, criterions, orders, 1L, request.getPageSize());
		List<OutMsgDTO> dtos = new ArrayList<>();
		for (ChatMsg e : result) {
			OutMsgDTO dto = new OutMsgDTO();
			dto.setId(e.getId());
			dto.setPic(e.getPicURL());
			dto.setText(e.getText());
			dto.setSentDate(e.getModification().getLastModifiedDate());
			ProfileDTO sender = this.getChatter(e.getFrom());
			// 如果没找到sender，忽略此条消息
			if (sender == null) {
				logger.debug("用户:{} 丢失！", e.getFrom());
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
			if (e.getTo() != null) {
				dto.setPrivacy(true);
			}
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<ChatPicDTO> getPicUrl(Long id, Long pageNumber, Integer pageSize) {
		List<ChatPicDTO> dtos = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fromId", id);
		List<ChatMsg> chatMsgs = this.dao.query(
				"from ChatMsg as c where c.from = :fromId and picURL is NOT null order by modification.creationDate desc", paramMap,
				pageNumber, pageSize);
		for (ChatMsg chatMsg : chatMsgs) {
			ChatPicDTO dto = new ChatPicDTO();
			if (chatMsg.getTo() == null) {
				if (chatMsg.getPicURL() != null && !chatMsg.getPicURL().isEmpty()) {
					dto.setPicUrl(chatMsg.getPicURL());
				}
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void sendRoomMsg(Outbound outbound, Long chatRoomId, WebSocketSession wsSession) {
		Set<WebSocketSession> targetSessions = this.getAllRoomChatterSessions(chatRoomId);
		if (CollectionUtils.isEmpty(targetSessions)) {
			// 很少出现，但是不排除本session也失效了，导致聊天室空，理论上自己的session和店铺的大屏端session会在
			this.sendErrorMsg("聊天室是空的！", wsSession);
			return;
		}

		this.sendOutMsg(outbound, targetSessions, wsSession);
	}

	@Override
	public void sendPrivacyMsg(Outbound outbound, Long to, WebSocketSession wsSession) {
		Set<WebSocketSession> targetSessions = this.getTargetChatterSessions(to);
		if (CollectionUtils.isEmpty(targetSessions)) {
			this.sendErrorMsg("对方已经下线！", wsSession);
			return;
		}
		this.sendOutMsg(outbound, targetSessions, wsSession);
	}

	@Override
	public void sendErrorMsg(String reason, WebSocketSession wsSession) {
		ErrorOutbound error = new ErrorOutbound(reason);
		this.sendMsg(error, wsSession);
	}

	@Transactional
	@Override
	public void markMsgPayed(long id) {
		ChatMsg msg = this.dao.get(ChatMsg.class, id);
		msg.setPayed(true);
	}

	@Override
	public ProfileDTO getDailyRicher(long shopId) {
		RicherDTO dailyRicher = this.richerReportCache.getDailyRicher(shopId);
		if(dailyRicher == null || dailyRicher.getChatterDTO() == null){
			return null;
		}
		ProfileDTO dto = this.getChatter(dailyRicher.getChatterDTO().getId());
		return dto;
	}
	
	@Override
	public void refreshDailyRicher(long shopId) {
		ProfileDTO dailyRicher = this.getDailyRicher(shopId);
		ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
		long chatRoomId = room.getId();
		this.sessionContainer.setRoomDailyRicher(shopId, chatRoomId, dailyRicher);
		this.boardRoomStatisticsInfo(chatRoomId);
	}

	private void sendOutMsg(Outbound outbound, Set<WebSocketSession> targetSessions, WebSocketSession selfSession) {
		for (WebSocketSession targetSession : targetSessions) {
			if (!targetSession.equals(selfSession)) {
				this.sendMsg(outbound, targetSession);
			}
		}
		this.sendAckMsg(outbound, selfSession);
	}

	private void sendAckMsg(Outbound outbound, WebSocketSession wsSession) {
		Outbound ackBound = (Outbound) outbound.clone();
		ackBound.setCommand(OutboundCommand.SEND_MSG_ACK);
		this.sendMsg(ackBound, wsSession);
	}

	private void sendMsg(Outbound outbound, WebSocketSession wsSession) {
		if (wsSession == null) {
			return;
		}
		this.chatMsgEventProducer.produce(wsSession.getId(), outbound);
	}


}
