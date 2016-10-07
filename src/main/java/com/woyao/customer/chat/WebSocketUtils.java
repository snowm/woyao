package com.woyao.customer.chat;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.chat.dto.InMsg;
import com.woyao.customer.dto.ChatterDTO;

public abstract class WebSocketUtils {

	public static String getHttpSessionId(WebSocketSession wsSession) {
		return (String) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_HTTPSESSION_ID);
	}

	public static ChatterDTO getChatter(WebSocketSession wsSession) {
		return (ChatterDTO) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_CHATTER);
	}

	public static Long getChatterId(WebSocketSession wsSession) {
		return (Long) getChatter(wsSession).getId();
	}

	public static Long getChatRoomId(WebSocketSession wsSession) {
		return (Long) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_CHATROOM_ID);
	}

	public static ReentrantLock getMsgCacheLock(WebSocketSession wsSession) {
		return (ReentrantLock) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_MSG_CACHE_LOCK);
	}

	@SuppressWarnings("unchecked")
	public static Map<Long, InMsg> getMsgCache(WebSocketSession wsSession) {
		return (Map<Long, InMsg>) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_MSG_CACHE);
	}
}
