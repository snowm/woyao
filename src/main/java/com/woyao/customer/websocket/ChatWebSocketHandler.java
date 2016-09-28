package com.woyao.customer.websocket;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("connectionEstablished:" + session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		try (LockScope scope = LockScope.write(this.sessionMapLock)) {
//			String nickName = this.sessionMap.removeValue(session);
//			if (nickName != null) {
//				for (WebSocketSession otherSession : this.sessionMap.values()) {
//					if (!Objects.equals(session, otherSession)) {
//						new RemoveUserResponse(nickName).send(otherSession);
//					}
//				}
//			}
		// }
		log.debug("connectionClosed:" + session.getId());
		super.afterConnectionClosed(session, status);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		String inMessage = message.getPayload();
		log.debug("inMessage:"+inMessage);
		session.sendMessage(new TextMessage(inMessage+"---return."));
	}
	
}
