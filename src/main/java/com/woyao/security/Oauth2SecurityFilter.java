package com.woyao.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.snowm.security.profile.domain.Gender;
import com.woyao.GlobalConfig;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.wx.WxEndpoint;
import com.woyao.wx.dto.GetAccessTokenResponse;
import com.woyao.wx.dto.GetUserInfoResponse;

@Component("oauth2SecurityFilter")
public class Oauth2SecurityFilter implements Filter, InitializingBean {

	private static final String PARA_OAUTH_CODE = "code";
	private static final String SESSION_ATTR_OAUTH_CODE = "code";

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Resource(name = "wxEndpoint")
	private WxEndpoint wxEndpoint;

	@Resource(name = "profileWxService")
	private IProfileWxService profileWxService;

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

		String currentUri = calculateCurrentUri(request);

		if (authorize(request, response)) {
			try {
				// 去掉微信授权的的code
				if (request.getParameterMap().containsKey(PARA_OAUTH_CODE)) {
					String url = this.removeCodeParam(request);
					this.redirectUser(request, response, url);
				}
				chain.doFilter(servletRequest, servletResponse);
				return;
			} catch (Exception ex) {
				throw ex;
			}
		}
		// 验证权限失败，重定向到微信授权网页
		String scope = "snsapi_userinfo";
		String state = System.currentTimeMillis() + "";
		String redirectUrl = this.calculateRedirectUrl(this.globalConfig.getAppId(), currentUri, scope, state);
		this.redirectUser(request, response, redirectUrl);
	}

	@Override
	public void destroy() {
	}

	private boolean authorize(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ChatterDTO dto = (ChatterDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
		if (dto != null) {
			return true;
		}

		String code = request.getParameter(PARA_OAUTH_CODE);
		if (StringUtils.isBlank(code)) {
			return false;
		}
		session.setAttribute(SESSION_ATTR_OAUTH_CODE, code);

		dto = this.getChatterInfo(code);
		if (dto == null) {
			return false;
		}
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATTER, dto);

		return true;
	}

	private String calculateRedirectUrl(String appId, String currentUrl, String scope, String state) {
		return String.format(this.globalConfig.getAuthorizeFormat(), appId, currentUrl, scope, state);
	}

	protected void redirectUser(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		this.redirectStrategy.sendRedirect(request, response, url);
	}

	/**
	 * Calculate the current URI given the request.
	 * 
	 * @param request
	 *            The request.
	 * @return The current uri.
	 */
	protected String calculateCurrentUri(HttpServletRequest request) throws UnsupportedEncodingException {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
		// Now work around SPR-10172...
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
		return ServletUriComponentsBuilder.fromUri(uri.toUri()).replaceQuery(query).build().toString();
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

	// mock code
	private AtomicLong idGenerator = new AtomicLong();
	
	public ChatterDTO createMockDTO(){
		long id = idGenerator.incrementAndGet();
		ChatterDTO dto = new ChatterDTO();
		dto.setId(id);
		dto.setNickname("nickname" + id);
		dto.setCity("city" + id);
		dto.setCountry("country" + id);
		dto.setHeadImg("/pic/head/" + ((id % 4) + 1) + ".jpg");
		Gender gender = Gender.FEMALE;
		if ((id % 2) == 0) {
			gender = Gender.FEMALE;
		}
		dto.setGender(gender);
		return dto;
	}

	private ChatterDTO getChatterInfo(String code) {
		if ("woyao".equals(code)) {
			return this.createMockDTO();
		}
		try {
			GetAccessTokenResponse tokenResponse = this.wxEndpoint.getAccessToken(globalConfig.getAppId(), globalConfig.getAppSecret(),
					code, "authorization_code");
			GetUserInfoResponse userInfoResponse = this.wxEndpoint.getUserInfo(tokenResponse.getAccessToken(), tokenResponse.getOpenid(),
					"zh_CN");
			String openId = userInfoResponse.getOpenid();
			if (StringUtils.isBlank(openId)) {
				throw new RuntimeException("open id blank!");
			}
			ChatterDTO dto = this.profileWxService.getByOpenId(openId);
			if (dto == null) {
				dto = new ChatterDTO();
			}
			BeanUtils.copyProperties(userInfoResponse, dto);
			dto.setGender(this.parseGender(userInfoResponse.getSex()));

			return this.profileWxService.saveChatterInfo(dto);
		} catch (Exception ex) {
			log.warn("Get user info from weixin failure!", ex);
			return null;
		}
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

}
