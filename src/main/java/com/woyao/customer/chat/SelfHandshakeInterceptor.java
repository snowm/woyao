package com.woyao.customer.chat;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

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
		log.debug("Before Handshake:" + rs);
		if (rs) {
			attributes.put(WebsocketSessionHttpSessionContainer.SESSION_ATTR_HTTPSESSION_ID, session);
		}
		return rs;
	}

	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(isCreateSession());
		}
		return null;
	}
}
