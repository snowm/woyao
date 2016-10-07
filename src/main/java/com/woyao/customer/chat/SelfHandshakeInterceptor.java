package com.woyao.customer.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.snowm.security.profile.domain.Gender;
import com.woyao.customer.chat.dto.InMsg;
import com.woyao.customer.dto.ChatterDTO;

public class SelfHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	private Log log = LogFactory.getLog(this.getClass());

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		HttpSession session = this.getSession(request);
		if (session == null) {
			if (log.isDebugEnabled()) {
				log.debug("http session is null");
			}
			return false;
		} else {
			if (log.isDebugEnabled()) {
				log.debug("http session exists:" + session.getId());
			}
		}

		// 解决The extension [x-webkit-deflate-frame] is not supported问题
		// if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
		// request.getHeaders().set("Sec-WebSocket-Extensions",
		// "permessage-deflate");
		// }

		boolean rs = super.beforeHandshake(request, response, wsHandler, attributes);
		ChatterDTO dto = this.generateDTO();
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATTER, dto);
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, 1L);
		
		attributes.put(SessionContainer.SESSION_ATTR_CHATTER, dto);
		attributes.put(SessionContainer.SESSION_ATTR_CHATROOM_ID, 1L);
		attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE_LOCK, new ReentrantLock());
		attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE, new HashMap<Long, InMsg>());
		log.debug("Before Handshake:" + rs);
		return rs;
	}

	private AtomicLong idGenerator = new AtomicLong();

	private ChatterDTO generateDTO() {
		long id = idGenerator.incrementAndGet();
		ChatterDTO dto = new ChatterDTO();
		dto.setId(id);
		dto.setNickname("nickname" + id);
		dto.setCity("city" + id);
		dto.setCountry("country" + id);
		dto.setHeadImg("/pic/head/" + ((id % 4) + 1) + ".jpg");
		dto.setGender(Gender.FEMALE);
		return dto;
	}

	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(isCreateSession());
		}
		return null;
	}
}
