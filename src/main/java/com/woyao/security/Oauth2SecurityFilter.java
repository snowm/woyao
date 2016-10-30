package com.woyao.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.snowm.security.profile.domain.Gender;
import com.woyao.GlobalConfig;
import com.woyao.customer.chat.session.SessionContainer;
import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.utils.CookieUtils;
import com.woyao.utils.UrlUtils;
import com.woyao.wx.WxEndpoint;
import com.woyao.wx.dto.GetUserInfoResponse;
import com.woyao.wx.service.IWxService;

@Component("oauth2SecurityFilter")
public class Oauth2SecurityFilter implements Filter, InitializingBean {

	public static final String PARA_OAUTH_CODE = "code";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@Resource(name = "wxEndpoint")
	private WxEndpoint wxEndpoint;

	@Resource(name = "profileWxService")
	private IProfileWxService profileWxService;

	@Resource(name = "wxService")
	private IWxService wxService;

	@Resource(name = "desCodec")
	private DESCodec desCodec;

	@Resource(name = "cookieUtils")
	private CookieUtils cookieUtils;

	@Value("${wx.authority.mock}")
	private boolean wxMock = true;

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
			response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器出错啦！请稍后重试。");
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
				return;
			} catch (Exception ex) {
				logger.error("Authorize error", ex);
				response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "授权失败，服务器出错啦！");
				return;
			}
		}
		// 验证权限失败，重定向到微信授权网页
		String scope = "snsapi_userinfo";
		String state = System.currentTimeMillis() + "";
		currentUri = URLEncoder.encode(currentUri, "UTF-8");
		String redirectUrl = this.generateOauthRedirectUrl(this.globalConfig.getAppId(), currentUri, scope, state);
		logger.debug("Authorize failure, redirect to : {}", redirectUrl);
		this.redirectUser(request, response, redirectUrl);
	}

	@Override
	public void destroy() {
	}

	private boolean authorize(HttpServletRequest request, HttpServletResponse response) {
		ProfileDTO dto = this.getUserInfo(request);
		if (dto == null || StringUtils.isBlank(dto.getOpenId())) {
			this.cookieUtils.deleteCookie(response, CookieConstants.OPEN_ID);
			return false;
		}

		// 获取到信息后，将chatter放进session，将openId放入cookie
		String encryptedOpenId = dto.getOpenId();
		try {
			encryptedOpenId = this.desCodec.encrypt(dto.getOpenId());
		} catch (Exception ex) {
			logger.warn("Encrypt openId error!", ex);
		}
		this.cookieUtils.setCookie(response, CookieConstants.OPEN_ID, encryptedOpenId);
		
		HttpSession session = request.getSession();
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATTER, dto);
		session.setAttribute(SessionContainer.SESSION_ATTR_ISDAPIN, false);
		return true;
	}
	

	private ProfileDTO getUserInfo(HttpServletRequest request) {
		String code = request.getParameter(PARA_OAUTH_CODE);
		if (this.wxMock && "woyao".equals(code)) {
			//mock模式，并且使用了测试code激活，可以在url里面直接带openId参数
			return this.mockProfile(request);
		}
		
		// 先尝试从session里面获取chatter信息
		HttpSession session = request.getSession();
		ProfileDTO dto = (ProfileDTO) session.getAttribute(SessionContainer.SESSION_ATTR_CHATTER);
		if (dto != null) {
			return dto;
		}
		logger.debug("从session里面没有获取到用户信息!");

		// 尝试根据openId或oauth code，从微信服务器获取chatter信息
		String openId = this.cookieUtils.getCookie(request, CookieConstants.OPEN_ID);
		if (!StringUtils.isBlank(openId)) {
			try {
				openId = this.desCodec.decrypt(openId);
			} catch (Exception ex) {
				logger.error("Decrypt openId error!", ex);
				return null;
			}
		}

		if (StringUtils.isBlank(openId) && StringUtils.isBlank(code)) {
			logger.debug("Authorize failure, openId 和 code 都没有！");
			return null;
		}
		
		// 先根据OpenID尝试从数据库中获取
		if (!StringUtils.isBlank(openId)) {
			dto = this.profileWxService.getByOpenId(openId);
			if (dto != null && !dto.isExpired()) {
				return dto;
			}
			if (dto == null) {
				logger.debug("根据openId:{}在数据库中查询用户，不存在!", openId);
			} else {
				logger.debug("用户openId:{}在数据库中的信息已经过期，需要刷新!", openId);
			}
		}
		GetUserInfoResponse userInfoResponse = this.getUserInfoFromWx(openId, code);
		if (userInfoResponse == null) {
			logger.debug("从微信没有获取到用户信息!");
			return null;
		}

		// 将用户信息入库
		dto = new ProfileDTO();
		BeanUtils.copyProperties(userInfoResponse, dto);
		dto.setGender(this.parseGender(userInfoResponse.getSex()));

		dto = this.profileWxService.saveProfileInfo(dto);
		dto.setLoginDate(new Date());
		return dto;
	}

	private String generateOauthRedirectUrl(String appId, String currentUrl, String scope, String state) {
		return String.format(this.globalConfig.getAuthorizeFormat(), appId, currentUrl, scope, state);
	}

	protected void redirectUser(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		response.sendRedirect(url);
	}

	private GetUserInfoResponse getUserInfoFromWx(String openId, String code) {
		GetUserInfoResponse userInfoResponse = null;
		if (!StringUtils.isBlank(openId)) {
			userInfoResponse = this.wxService.getUserInfoViaExistedOpenId(openId, globalConfig.getAppId(), globalConfig.getAppSecret());
			logger.debug("wxService.getUserInfoViaExistedOpenId:{}", userInfoResponse);
		}
		if (userInfoResponse == null && !StringUtils.isBlank(code)) {
			userInfoResponse = this.wxService.getUserInfo(globalConfig.getAppId(), globalConfig.getAppSecret(), code);
			logger.debug("wxService.getUserInfo:{}", userInfoResponse);
		}
		if (userInfoResponse == null || StringUtils.isBlank(userInfoResponse.getOpenId())) {
			logger.error("不能从微信获取到用户信息!");
			return null;
		}
		return userInfoResponse;
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

	private ProfileDTO mockProfile(HttpServletRequest request){
		String mockOpenId = request.getParameter("openId");
		//mock模式，并且使用了测试code激活，可以在url里面直接带openId参数
		if (!StringUtils.isBlank(mockOpenId)) {
			String openId = mockOpenId;
			logger.debug("测试模式: url指定openId[{}],此opendId必须已经存在,仅用于调试!", openId);
			ProfileDTO dto = this.profileWxService.getByOpenId(openId);
			if (dto == null) {
				logger.error("测试模式: url指定openId[{}]的用户不存在!", openId);
			}
			return dto;
		}
		//如果url里面没有带openId参数，那么mock一个用户信息
		GetUserInfoResponse userInfoResponse = this.createMockResponse();

		// 将用户信息入库
		ProfileDTO profileDTO = new ProfileDTO();
		BeanUtils.copyProperties(userInfoResponse, profileDTO);
		profileDTO.setGender(this.parseGender(userInfoResponse.getSex()));

		profileDTO = this.profileWxService.saveProfileInfo(profileDTO);
		profileDTO.setLoginDate(new Date());
		return profileDTO;
	}
	
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

}
