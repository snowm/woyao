package com.woyao.customer.chat;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;

import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.chat.in.InMsg;

public abstract class SessionUtils {

	public static String getHttpSessionId(WebSocketSession wsSession) {
		return (String) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_HTTPSESSION_ID);
	}

	public static ChatterDTO getChatter(WebSocketSession wsSession) {
		return (ChatterDTO) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_CHATTER);
	}

	public static Long getChatterId(WebSocketSession wsSession) {
		ChatterDTO dto = getChatter(wsSession);
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
	public static Map<Long, InMsg> getMsgCache(WebSocketSession wsSession) {
		return (Map<Long, InMsg>) wsSession.getAttributes().get(SessionContainer.SESSION_ATTR_MSG_CACHE);
	}

	public static ChatterDTO getChatter(HttpSession session) {
		return (ChatterDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
	}

	public static Long getChatterId(HttpSession session) {
		ChatterDTO dto = (ChatterDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
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

}
