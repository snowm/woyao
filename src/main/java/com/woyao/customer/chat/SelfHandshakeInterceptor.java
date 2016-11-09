package com.woyao.customer.chat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.snowm.security.profile.domain.Gender;
import com.woyao.customer.chat.session.SessionContainer;
import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.dto.chat.in.EntireInMsg;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.wx.dto.GetUserInfoResponse;

public class SelfHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private boolean perfTestMode = false;

	@Resource(name = "profileWxService")
	private IProfileWxService profileWxService;

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		HttpSession session = this.getSession(request);
		if (session == null) {
			if (this.perfTestMode) {
				ProfileDTO dto = this.generateMockProfile();
				attributes.put(SessionContainer.SESSION_ATTR_HTTPSESSION_ID, "testSession");
				attributes.put(SessionContainer.SESSION_ATTR_ISDAPIN, false);
				attributes.put(SessionContainer.SESSION_ATTR_CHATTER, dto);
				long chatRoomId = this.getRoomId();
				attributes.put(SessionContainer.SESSION_ATTR_CHATROOM_ID, chatRoomId);
				attributes.put(SessionContainer.SESSION_ATTR_SHOP_ID, chatRoomId);
				attributes.put(SessionContainer.SESSION_ATTR_REMOTE_IP, "127.0.0.1");
				attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE_LOCK, new ReentrantLock());
				attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE, new HashMap<Long, EntireInMsg>());
				return true;
			}
			logger.error("While do the handShake for websocket, http session does not exist!");
			return false;
		}

		ProfileDTO dto = (ProfileDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
		if (dto == null) {
			logger.error("While do the handShake for websocket, can not get the profile info from http session!");
			return false;
		}
		Long chatRoomId = SessionUtils.getChatRoomId(session);
		Boolean isDapin = (Boolean) session.getAttribute(SessionContainer.SESSION_ATTR_ISDAPIN);
		if (isDapin == null) {
			isDapin = false;
		}
		String remoteAddress = this.getClientIp(request);

		logger.debug("handshake with httpSession:" + session.getId());
		attributes.put(SessionContainer.SESSION_ATTR_HTTPSESSION_ID, session.getId());
		attributes.put(SessionContainer.SESSION_ATTR_HTTPSESSION, session);
		attributes.put(SessionContainer.SESSION_ATTR_ISDAPIN, isDapin);
		attributes.put(SessionContainer.SESSION_ATTR_CHATTER, dto);
		attributes.put(SessionContainer.SESSION_ATTR_CHATROOM_ID, chatRoomId);
		attributes.put(SessionContainer.SESSION_ATTR_REMOTE_IP, remoteAddress);
		attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE_LOCK, new ReentrantLock());
		attributes.put(SessionContainer.SESSION_ATTR_MSG_CACHE, new HashMap<Long, EntireInMsg>());

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

	public void setPerfTestMode(boolean perfTestMode) {
		this.perfTestMode = perfTestMode;
	}

	private ProfileDTO generateMockProfile() {
		GetUserInfoResponse userInfoResponse = this.createMockResponse();
		return this.saveChatterInfo(userInfoResponse);
	}

	private ProfileDTO saveChatterInfo(GetUserInfoResponse userInfoResponse) {
		// 将用户信息入库
		ProfileDTO dto = new ProfileDTO();
		BeanUtils.copyProperties(userInfoResponse, dto);
		dto.setGender(this.parseGender(userInfoResponse.getSex()));

		dto = this.profileWxService.saveProfileInfo(dto);
		dto.setLoginDate(new Date());
		return dto;
	}

	private Gender parseGender(String sex) {
		if (sex == null) {
			return Gender.OTHER;
		}
		switch (sex) {
		case "2":
			return Gender.FEMALE;
		case "1":
			return Gender.MALE;
		default:
			return Gender.OTHER;
		}
	}

	// mock code
	private AtomicLong seqGenerator = new AtomicLong(0L);

	public GetUserInfoResponse createMockResponse() {
		long seq = seqGenerator.decrementAndGet();
		GetUserInfoResponse resp = new GetUserInfoResponse();
		String id = UUID.randomUUID().toString();
		resp.setOpenId("openId[" + id + "]");
		resp.setNickname("昵称[" + id + "]");
		resp.setCity("城市[" + seq + "]");
		resp.setCountry("国家[" + seq + "]");
		resp.setHeadImg("/pic/head/" + ((Math.abs(seq) % 4) + 1) + ".jpg");
		String gender = "2";
		if ((seq % 2) == 0) {
			gender = "1";
		}
		resp.setSex(gender);
		return resp;
	}

	AtomicLong rIdGen = new AtomicLong(1L);

	private long getRoomId() {
		long id = rIdGen.getAndIncrement();
		id = id % 20L;
		if (id < 1L) {
			return -20L;
		}
		return -id;
	}
}
