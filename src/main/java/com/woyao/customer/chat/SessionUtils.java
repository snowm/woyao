package com.woyao.customer.chat;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;

import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.dto.chat.in.EntireInMsg;

public abstract class SessionUtils {

	public static String getHttpSessionId(WebSocketSession wsSession) {
		return (String) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_HTTPSESSION_ID);
	}

	public static HttpSession getHttpSession(WebSocketSession wsSession) {
		return (HttpSession) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_HTTPSESSION);
	}

	public static ProfileDTO getChatter(WebSocketSession wsSession) {
		return (ProfileDTO) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_CHATTER);
	}

	public static Long getChatterId(WebSocketSession wsSession) {
		ProfileDTO dto = getChatter(wsSession);
		if (dto == null) {
			return null;
		}
		return dto.getId();
	}

	public static Long getChatRoomId(WebSocketSession wsSession) {
		return (Long) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_CHATROOM_ID);
	}

	public static Long getShopId(WebSocketSession wsSession) {
		return (Long) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_SHOP_ID);
	}

	public static ReentrantLock getMsgCacheLock(WebSocketSession wsSession) {
		return (ReentrantLock) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_MSG_CACHE_LOCK);
	}

	@SuppressWarnings("unchecked")
	public static Map<Long, EntireInMsg> getMsgCache(WebSocketSession wsSession) {
		return (Map<Long, EntireInMsg>) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_MSG_CACHE);
	}

	public static ProfileDTO getChatter(HttpSession session) {
		return (ProfileDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
	}

	public static Long getChatterId(HttpSession session) {
		ProfileDTO dto = (ProfileDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
		if (dto == null) {
			return null;
		}
		return dto.getId();
	}

	public static Long getChatRoomId(HttpSession session) {
		return (Long) session.getAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID);
	}

	public static Long getShopId(HttpSession session) {
		return (Long) session.getAttribute(SessionContainer.SESSION_ATTR_SHOP_ID);
	}

	public static String getRemoteIp(WebSocketSession wsSession) {
		return (String) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_REMOTE_IP);
	}

	public static boolean isDapin(WebSocketSession wsSession) {
		Object value = wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_REMOTE_IP);
		if (value == null) {
			return false;
		}
		return (Boolean) value;
	}

}
