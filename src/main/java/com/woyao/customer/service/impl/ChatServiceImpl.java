package com.woyao.customer.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

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
		if (inMsg != null) {
			StringBuilder sb = new StringBuilder();
			for (BlockDTO block : inMsg.getBlocks()) {
				sb.append(block.getBlock());
			}
			try {
				InMsg tmpMsg = JsonUtils.toObject(sb.toString(), InMsg.class);
				inMsg.setText(tmpMsg.getText());
				inMsg.setPic(tmpMsg.getPic());

				long id = this.saveMsg(inMsg);

				OutMsgDTO outMsg = new OutMsgDTO();
				outMsg.setId(id);
				outMsg.setSender(WebSocketUtils.getChatter(wsSession));
				outMsg.setText(inMsg.getText());
				outMsg.setCommand(OutboundCommand.SEND_MSG);
				outMsg.setDuration(0);
				String base64PicString = inMsg.getPic();
				if (!StringUtils.isBlank(base64PicString)) {
					String path = "/pic/" + ChatUtils.savePic(base64PicString);
					log.info("saved pic:" + path);
					outMsg.setPic(path);
				}
				this.sendOutMsg(outMsg, inMsg.getTo(), WebSocketUtils.getChatRoomId(wsSession));
			} catch (IOException e) {
				log.error("Process message failure!", e);
			}
		}
	}

	private AtomicLong msgIdGenerator = new AtomicLong();

	private long getMsgId() {
		return msgIdGenerator.getAndIncrement();
	}

	private long saveMsg(InMsg msg) {
		// TODO
		return this.getMsgId();
	}

	public void sendOutMsg(Outbound outbound, Long to, Long chatRoomId) {
		Set<WebSocketSession> targetSessions = new HashSet<>();
		if (to == null && chatRoomId != null) {
			targetSessions = this.getAllRoomChatterSessions(chatRoomId);
		} else {
			targetSessions = this.getTargetChatterSessions(to);
		}
		if (targetSessions == null || targetSessions.isEmpty()) {
			return;
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
	public List<ChatterDTO> listOnlineChatters(long chatRoomId) {
		Set<WebSocketSession> wsSessions = this.sessionContainer.getWsSessionOfRoom(chatRoomId);
		List<ChatterDTO> dtos = new ArrayList<>();
		if (wsSessions != null && !wsSessions.isEmpty()) {
			for (WebSocketSession wsSession : wsSessions) {
				dtos.add(WebSocketUtils.getChatter(wsSession));
			}
		}
		return dtos;
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
