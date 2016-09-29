package com.woyao.customer.chat;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.woyao.customer.chat.data.AddUserOutbound;
import com.woyao.customer.chat.data.BroadcastInbound;
import com.woyao.customer.chat.data.ErrorOutbound;
import com.woyao.customer.chat.data.Inbound;
import com.woyao.customer.chat.data.LockScope;
import com.woyao.customer.chat.data.MessageOutbound;
import com.woyao.customer.chat.data.RemoveUserResponse;
import com.woyao.customer.chat.data.SendingInbound;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	
	private Log log = LogFactory.getLog(this.getClass());

	private BidiMap<String, WebSocketSession> sessionMap = new DualLinkedHashBidiMap<>();
	
	private ReadWriteLock sessionMapLock = new ReentrantReadWriteLock();

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		try (LockScope scope = LockScope.write(this.sessionMapLock)) {
			String nickName = this.sessionMap.removeValue(session);
			if (nickName != null) {
				for (WebSocketSession otherSession : this.sessionMap.values()) {
					if (!Objects.equals(session, otherSession)) {
						new RemoveUserResponse(nickName).send(otherSession);
					}
				}
			}
		}
		log.debug("connectionClosed:" + session.getId());
		super.afterConnectionClosed(session, status);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		Inbound request = Inbound.parse(message.getPayload());
		try {
			if (request instanceof BroadcastInbound) {
				this.broadcast(session, (BroadcastInbound)request);
			} else if (request instanceof SendingInbound) {
				this.send(session, (SendingInbound)request);
			}
		} catch (RuntimeException | Error ex) {
			new ErrorOutbound(ex.getMessage()).send(session);
		}
	}
	
	private void broadcast(WebSocketSession session, BroadcastInbound request) throws IOException {
		try (LockScope scope = LockScope.read(this.sessionMapLock)) {
			String from = this.sessionMap.getKey(session);
			for (WebSocketSession otherSession : this.sessionMap.values()) {
				if (!Objects.equals(session, otherSession)) {
					new MessageOutbound(from, request.getMessage()).send(otherSession);
				}
			}
		}
	}
	
	private void send(WebSocketSession session, SendingInbound request) throws IOException {
		try (LockScope scope = LockScope.read(this.sessionMapLock)) {
			WebSocketSession toSession = this.sessionMap.get(request.getTo());
			if (toSession != null) {
				new MessageOutbound(this.sessionMap.getKey(session), request.getTo(), request.getMessage()).send(toSession);
			}
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("connectionEstablished:" + session.getId());

		String nickName = "昵称:"+session.getId();
		try (LockScope scope = LockScope.write(this.sessionMapLock)) {
			if (this.sessionMap.containsKey(nickName)) {
				throw new IllegalArgumentException(nickName + " is used by some body");
			}
			if (this.sessionMap.containsValue(session)) {
				throw new IllegalArgumentException("Don't do duplicated login");
			}
			for (WebSocketSession otherSession : this.sessionMap.values()) {
				if (!Objects.equals(session, otherSession)) {
					new AddUserOutbound(nickName).send(otherSession);
				}
			}
			this.sessionMap.put(nickName, session);
		}
	}

}
