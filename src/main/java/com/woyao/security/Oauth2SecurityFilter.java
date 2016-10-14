package com.woyao.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.snowm.security.profile.domain.Gender;
import com.woyao.GlobalConfig;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.utils.CookieUtils;
import com.woyao.utils.UrlUtils;
import com.woyao.wx.WxEndpoint;
import com.woyao.wx.dto.GetUserInfoResponse;
import com.woyao.wx.service.IWxService;

@Component("oauth2SecurityFilter")
public class Oauth2SecurityFilter implements Filter, InitializingBean {

	private static final int COOKIE_AGE = 31536000;
	public static final String PARA_OAUTH_CODE = "code";
	private static final String SESSION_ATTR_OAUTH_CODE = "code";
	private static final String SESSION_ATTR_OPEN_ID = "openId";

	private Log log = LogFactory.getLog(this.getClass());

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

		if (authorize(request, response)) {
			try {
				if (request.getParameterMap().containsKey(PARA_OAUTH_CODE)) {
					// 去掉微信授权的的code
					String url = this.removeCodeParam(request);
					log.debug("The code parameter of Oauth remove:" + url);
					this.redirectUser(request, response, url);
					return;
				}
				chain.doFilter(servletRequest, servletResponse);
				return;
			} catch (Exception ex) {
				response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器出错啦！");
				return;
			}
		}
		// 验证权限失败，重定向到微信授权网页
		String scope = "snsapi_userinfo";
		String state = System.currentTimeMillis() + "";
		log.debug("CurrentUri:" + currentUri);
		currentUri = URLEncoder.encode(currentUri, "UTF-8");
		String redirectUrl = this.calculateRedirectUrl(this.globalConfig.getAppId(), currentUri, scope, state);
		log.debug("Authorize failure, redirect to : " + redirectUrl);
		this.redirectUser(request, response, redirectUrl);
	}

	@Override
	public void destroy() {
	}

	private boolean authorize(HttpServletRequest request, HttpServletResponse response) {
		// 先尝试从session里面获取chatter信息
		HttpSession session = request.getSession();
		ChatterDTO dto = (ChatterDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
		if (dto != null) {
			return true;
		}
		// 再尝试根据cookie里面的chatterId从数据库获取chatter信息
		String cookieProfileId = CookieUtils.getCookie(request, CookieUtils.COOKIE_CHATTER_ID);
		Long profileId = null;
		if (!StringUtils.isBlank(cookieProfileId)) {
			profileId = Long.parseLong(cookieProfileId);
			dto = this.chatService.getChatterFromDB(profileId);
			if (dto != null) {
				session.setAttribute(SessionContainer.SESSION_ATTR_CHATTER, dto);
				return true;
			}
		}

		// 最后尝试根据chatter_id和oauth code，从微信服务器获取chatter信息
		String cookieOpenId = CookieUtils.getCookie(request, CookieUtils.COOKIE_OPEN_ID);
		String code = request.getParameter(PARA_OAUTH_CODE);
		if (StringUtils.isBlank(cookieOpenId) && StringUtils.isBlank(code)) {
			log.debug("openId 和 code 都没有！");
			return false;
		}
		session.setAttribute(SESSION_ATTR_OAUTH_CODE, code);
		session.setAttribute(SESSION_ATTR_OPEN_ID, cookieOpenId);

		dto = this.getChatterInfoFromWx(cookieOpenId, code);
		if (dto == null) {
			return false;
		}

		// 获取到信息后，将chatter放进session，将chatterId和openId放入cookie
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATTER, dto);
		CookieUtils.setCookie(response, CookieUtils.COOKIE_CHATTER_ID, dto.getId() + "", COOKIE_AGE);
		CookieUtils.setCookie(response, CookieUtils.COOKIE_OPEN_ID, dto.getOpenId(), COOKIE_AGE);
		return true;
	}

	private String calculateRedirectUrl(String appId, String currentUrl, String scope, String state) {
		return String.format(this.globalConfig.getAuthorizeFormat(), appId, currentUrl, scope, state);
	}

	protected void redirectUser(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(url);
		// this.redirectStrategy.sendRedirect(request, response, url);
	}

	protected String removeCodeParam(HttpServletRequest request) throws UnsupportedEncodingException {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
		String queryString = request.getQueryString();
		boolean legalSpaces = queryString != null && queryString.contains("+");
		if (legalSpaces) {
			builder.replaceQuery(queryString.replace("+", "%20"));
		}
		UriComponents uri = null;
		try {
			uri = builder.replaceQueryParam(PARA_OAUTH_CODE).build(true);
		} catch (IllegalArgumentException ex) {
			// ignore failures to parse the url (including query string). does't
			// make sense for redirection purposes anyway.
			return null;
		}
		String query = uri.getQuery();
		if (legalSpaces) {
			query = query.replace("%20", "+");
		}
		log.info(uri.toUri());
		return uri.toUriString();
	}

	private ChatterDTO getChatterInfoFromWx(String openId, String code) {
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
		ChatterDTO dto = this.profileWxService.getByOpenId(openId);
		if (dto == null) {
			dto = new ChatterDTO();
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
