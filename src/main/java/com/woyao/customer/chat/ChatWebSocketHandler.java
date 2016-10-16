package com.woyao.customer.chat;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.woyao.customer.chat.handler.MsgHandler;
import com.woyao.customer.dto.chat.in.Inbound;
import com.woyao.customer.service.IChatService;
import com.woyao.security.SharedHttpSessionContext;

public class ChatWebSocketHandler extends AbstractWebSocketHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "unifiedMsgHandler")
	private MsgHandler<Inbound> msgHandler;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Override
	public void afterConnectionClosed(WebSocketSession wsSession, CloseStatus status) throws Exception {
		super.afterConnectionClosed(wsSession, status);
		this.chatService.leave(wsSession);
		logger.debug("WebSocket session: {} closed!", wsSession.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession wsSession, TextMessage message) throws IOException {
		String payload = message.getPayload();
		logger.debug("Recieved msg: \n{}", payload);
		String remoteAddress = SessionUtils.getRemoteIp(wsSession);
		if (StringUtils.isBlank(payload)) {
			return;
		}
		Inbound inbound = Inbound.parse(payload);
		inbound.setRemoteAddress(remoteAddress);
		try {
			this.msgHandler.handle(wsSession, inbound);
		} catch (Exception ex) {
			logger.error("Handle message error!", ex);
			chatService.sendErrorMsg(MsgErrorConstants.ERR_SERVER_INTERNAL, wsSession);
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession wsSession) throws Exception {
		try {
			String httpSessionId = SessionUtils.getHttpSessionId(wsSession);
			HttpSession httpSession = SharedHttpSessionContext.getSession(httpSessionId);
			chatService.newChatter(wsSession, httpSession);

			logger.debug("WebSocket session: {} established, httpSessionId: {}", wsSession.getId(), httpSession.getId());
		} catch (Exception ex) {
			logger.error("WebSocket session: {} establish error!", ex);
		}
	}

}
