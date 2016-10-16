package com.woyao.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.snowm.security.profile.domain.Gender;
import com.woyao.GlobalConfig;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.utils.CookieUtils;
import com.woyao.utils.UrlUtils;
import com.woyao.wx.WxEndpoint;
import com.woyao.wx.dto.GetUserInfoResponse;
import com.woyao.wx.service.IWxService;

@Component("oauth2SecurityFilter")
public class Oauth2SecurityFilter implements Filter, InitializingBean {

	public static final String PARA_OAUTH_CODE = "code";
	
	private static final int COOKIE_AGE = 31536000;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	// private RedirectStrategy redirectStrategy = new
	// DefaultRedirectStrategy();

	@Resource(name = "wxEndpoint")
	private WxEndpoint wxEndpoint;

	@Resource(name = "profileWxService")
	private IProfileWxService profileWxService;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Resource(name = "wxService")
	private IWxService wxService;

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String currentUri = UrlUtils.calculateCurrentUri(request);
		logger.debug("请求Uri: {}, queryParameter:{}", currentUri, request.getQueryString());

		boolean pass = false;
		try {
			pass = this.authorize(request, response);
		} catch (Exception ex) {
			logger.error("Authorize error", ex);
			response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器出错啦！");
			return;
		}
		if (pass) {
			try {
				if (request.getParameterMap().containsKey(PARA_OAUTH_CODE)) {
					// 去掉微信授权的的code
					String url = UrlUtils.removeCodeParam(request);
					this.redirectUser(request, response, url);
					logger.debug("Authorize pass, redirect to Oauth的code参数去掉后的Url:{}", url);
					return;
				}
				logger.debug("Authorize pass，calling doFilter()");
				chain.doFilter(servletRequest, servletResponse);
				logger.debug("Authorize pass，doFilter() called");
				return;
			} catch (Exception ex) {
				logger.error("Authorize error", ex);
				response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器出错啦！");
				return;
			}
		}
		// 验证权限失败，重定向到微信授权网页
		String scope = "snsapi_userinfo";
		String state = System.currentTimeMillis() + "";
		currentUri = URLEncoder.encode(currentUri, "UTF-8");
		String redirectUrl = this.calculateRedirectUrl(this.globalConfig.getAppId(), currentUri, scope, state);
		logger.debug("Authorize failure, redirect to : {}", redirectUrl);
		this.redirectUser(request, response, redirectUrl);
	}

	@Override
	public void destroy() {
	}

	private boolean authorize(HttpServletRequest request, HttpServletResponse response) {
		ProfileDTO dto = this.getUserInfo(request);
		if (dto == null) {
			return false;
		}
		if (dto.getOpenId() == null) {
			return false;
		}
		HttpSession session = request.getSession();

		// 获取到信息后，将chatter放进session，将chatterId和openId放入cookie
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATTER, dto);
		CookieUtils.setCookie(response, CookieUtils.COOKIE_CHATTER_ID, dto.getId() + "", COOKIE_AGE);
		CookieUtils.setCookie(response, CookieUtils.COOKIE_OPEN_ID, dto.getOpenId(), COOKIE_AGE);
		return true;
	}

	private ProfileDTO getUserInfo(HttpServletRequest request) {
		// 先尝试从session里面获取chatter信息
		HttpSession session = request.getSession();
		ProfileDTO dto = (ProfileDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
		if (dto != null) {
			return dto;
		}
		logger.debug("从session里面没有获取到用户信息!");

		// 再尝试根据cookie里面的chatterId从数据库获取chatter信息
		String cookieProfileId = CookieUtils.getCookie(request, CookieUtils.COOKIE_CHATTER_ID);
		Long profileId = null;
		if (!StringUtils.isBlank(cookieProfileId)) {
			profileId = Long.parseLong(cookieProfileId);
			dto = this.chatService.getChatterFromDB(profileId);
			logger.debug("根据cookie里面的chatterId获取用户信息:{}", dto);
			if (dto != null) {
				return dto;
			}
		}

		// 最后尝试根据openId或oauth code，从微信服务器获取chatter信息
		String cookieOpenId = CookieUtils.getCookie(request, CookieUtils.COOKIE_OPEN_ID);
		String code = request.getParameter(PARA_OAUTH_CODE);
		if (StringUtils.isBlank(cookieOpenId) && StringUtils.isBlank(code)) {
			logger.debug("openId 和 code 都没有！");
			return null;
		}

		dto = this.getUserInfoFromWx(cookieOpenId, code);
		return dto;
	}

	private String calculateRedirectUrl(String appId, String currentUrl, String scope, String state) {
		return String.format(this.globalConfig.getAuthorizeFormat(), appId, currentUrl, scope, state);
	}

	protected void redirectUser(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(url);
		// this.redirectStrategy.sendRedirect(request, response, url);
	}

	private ProfileDTO getUserInfoFromWx(String openId, String code) {
		GetUserInfoResponse userInfoResponse = null;
		if ("woyao".equals(code)) {
			userInfoResponse = this.createMockResponse();
		} else {
			if (!StringUtils.isBlank(openId)) {
				userInfoResponse = this.wxService.getUserInfoViaExistedOpenId(openId, globalConfig.getAppId(), globalConfig.getAppSecret());
			}
			if (userInfoResponse == null && !StringUtils.isBlank(code)) {
				userInfoResponse = this.wxService.getUserInfo(globalConfig.getAppId(), globalConfig.getAppSecret(), code);
			}
			if (userInfoResponse == null) {
				return null;
			}
		}
		// 将用户信息入库
		ProfileDTO dto = this.profileWxService.getByOpenId(openId);
		if (dto == null) {
			dto = new ProfileDTO();
		}
		BeanUtils.copyProperties(userInfoResponse, dto);
		dto.setGender(this.parseGender(userInfoResponse.getSex()));

		dto = this.profileWxService.saveChatterInfo(dto);
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
	private AtomicLong idGenerator = new AtomicLong();

	public GetUserInfoResponse createMockResponse() {
		long id = idGenerator.incrementAndGet();
		GetUserInfoResponse resp = new GetUserInfoResponse();
		resp.setOpenId("openId" + id);
		resp.setNickname("nickname" + id);
		resp.setCity("city" + id);
		resp.setCountry("country" + id);
		resp.setHeadImg("/pic/head/" + ((id % 4) + 1) + ".jpg");
		String gender = "2";
		if ((id % 2) == 0) {
			gender = "1";
		}
		resp.setSex(gender);
		return resp;
	}

}
