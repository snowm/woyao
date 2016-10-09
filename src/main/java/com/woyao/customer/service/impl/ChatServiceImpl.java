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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import com.snowm.utils.query.PaginationBean;
import com.woyao.JsonUtils;
import com.woyao.customer.chat.ChatUtils;
import com.woyao.customer.chat.MessageCacheOperator;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.chat.WebSocketUtils;
import com.woyao.customer.chat.dto.BlockDTO;
import com.woyao.customer.chat.dto.InMsg;
import com.woyao.customer.chat.dto.Inbound;
import com.woyao.customer.chat.dto.OutMsgDTO;
import com.woyao.customer.chat.dto.Outbound;
import com.woyao.customer.chat.dto.OutboundCommand;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.utils.PaginationUtils;

@Component("chatService")
public class ChatServiceImpl implements IChatService {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "sessionContainer")
	private SessionContainer sessionContainer;

	@Resource(name = "messageCacheOperator")
	private MessageCacheOperator messageCacheOperator;

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
		Lock lock = WebSocketUtils.getMsgCacheLock(wsSession);
		Map<Long, InMsg> cache = WebSocketUtils.getMsgCache(wsSession);
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
				log.info("saved pic:" + path);
				inMsg.setPic(path);
			}

			Long chatRoomId = WebSocketUtils.getChatRoomId(wsSession);
			ChatterDTO sender = WebSocketUtils.getChatter(wsSession);
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
		m.setContent(msg.getText() + "\n" + msg.getPic());
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
		if (!targetSessions.contains(wsSession)) {
			targetSessions.add(wsSession);
		}
		for (WebSocketSession session : targetSessions) {
			try {
				outbound.send(session);
			} catch (IOException e) {
				log.warn("Msg send fail to session:" + session.getId(), e);
			}
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
		return null;
	}

	@Override
	public PaginationBean<ChatterDTO> listOnlineChatters(long chatRoomId, long pageNumber, int pageSize) {
		Set<WebSocketSession> wsSessions = this.sessionContainer.getWsSessionOfRoom(chatRoomId);
		List<ChatterDTO> dtos = new ArrayList<>();
		if (wsSessions != null && !wsSessions.isEmpty()) {
			for (WebSocketSession wsSession : wsSessions) {
				dtos.add(WebSocketUtils.getChatter(wsSession));
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

}
