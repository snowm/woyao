package com.woyao.customer.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.dto.chat.in.InMsg;

public class SelfHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		HttpSession session = this.getSession(request);
		if (session == null) {
			logger.error("While do the handShake for websocket, http session does not exist!");
			return false;
		}

		ProfileDTO dto = (ProfileDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
		if (dto == null) {
			logger.error("While do the handShake for websocket, can not get the profile info from http session!");
			return false;
		}
		Long chatRoomId = (Long) session.getAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID);
		String remoteAddress = this.getClientIp(request);

		logger.debug("handshake with httpSession:" + session.getId());
		attributes.put(SessionContainer.SESSION_ATTR_HTTPSESSION_ID, session.getId());
		attributes.put(SessionContainer.SESSION_ATTR_HTTPSESSION, session);
		attributes.put(SessionContainer.SESSION_ATTR_CHATTER, dto);
		attributes.put(SessionContainer.SESSION_ATTR_CHATROOM_ID, chatRoomId);
		attributes.put(SessionContainer.SESSION_ATTR_REMOTE_IP, remoteAddress);
		attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE_LOCK, new ReentrantLock());
		attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE, new HashMap<Long, InMsg>());
		

		boolean rs = super.beforeHandshake(request, response, wsHandler, attributes);
		return rs;
	}

	private String getClientIp(ServerHttpRequest request) {
		HttpHeaders headers = request.getHeaders();

		String ip = headers.getFirst("Origin-Client-Addr");
		logger.debug("headers.getFirst(\"Origin-Client-Addr\")=" + ip);

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("x-forwarded-for");
			logger.debug("headers.getFirst(\"x-forwarded-for\")={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("X-Forwarded-For");
			logger.debug("headers.getFirst(\"X-Forwarded-For\")={}", ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("Proxy-Client-IP");
			logger.debug("headers.getFirst(\"Proxy-Client-IP\")={}", ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("WL-Proxy-Client-IP");
			logger.debug("headers.getFirst(\"WL-Proxy-Client-IP\")={}", ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_CLIENT_IP");
			logger.debug("headers.getFirst(\"HTTP_CLIENT_IP\")={}", ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
			logger.debug("headers.getFirst(\"HTTP_X_FORWARDED_FOR\")={}", ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getAddress().getHostAddress();
			logger.debug("request.getRemoteAddress().getAddress().getHostAddress()={}", ip);
		}

		if (null != ip && ip.indexOf(',') != -1) {
			// 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串 IP 值
			// 取X-Forwarded-For中第一个非unknown的有效IP字符串
			// 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
			// 192.168.1.100
			// 用户真实IP为： 192.168.1.110
			// 注意:当访问地址为 localhost 时 地址格式为 0:0:0:0:0:0:1
			logger.debug("ip=", ip);
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				if (null != ips[i] && !"unknown".equalsIgnoreCase(ips[i])) {
					ip = ips[i];
					break;
				}
			}
			if ("0:0:0:0:0:0:1".equals(ip)) {
				logger.warn("由于客户端访问地址使用 localhost，获取客户端真实IP地址错误，请使用IP方式访问");
			}
		}

		if ("unknown".equalsIgnoreCase(ip)) {
			logger.warn("由于客户端通过Squid反向代理软件访问，获取客户端真实IP地址错误，请更改squid.conf配置文件forwarded_for项默认是为on解决");
		}
		logger.debug("client ip: {}", ip);
		return ip;
	}

	private HttpSession getSession(ServerHttpRequest request) {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(false);
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void dd(ServerHttpRequest request) {
		// 解决The extension [x-webkit-deflate-frame] is not supported问题
		if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
			request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
		}
	}

}
