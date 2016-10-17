package com.woyao.wx.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.woyao.customer.service.IProfileWxService;
import com.woyao.domain.wx.UserAccessToken;
import com.woyao.service.UserAccessTokenService;
import com.woyao.wx.WxEndpoint;
import com.woyao.wx.dto.GetAccessTokenResponse;
import com.woyao.wx.dto.GetUserInfoResponse;
import com.woyao.wx.service.IWxService;

@Service("wxService")
public class WxServiceImpl implements IWxService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "profileWxService")
	private IProfileWxService profileWxService;

	@Resource(name = "userAccessTokenService")
	private UserAccessTokenService userAccessTokenService;

	@Resource(name = "wxEndpoint")
	private WxEndpoint wxEndpoint;

	@Override
	public GetUserInfoResponse getUserInfoViaExistedOpenId(String openId, String appId, String appSecret) {
		String accessToken = null;

		UserAccessToken tokenDomain = this.userAccessTokenService.getTokenByOpenId(openId);
		if (tokenDomain != null && !tokenDomain.isExpired()) {
			accessToken = tokenDomain.getAccessToken();
		} else if (tokenDomain.isExpired()) {
			GetAccessTokenResponse tokenResponse = this.wxEndpoint.refreshAccessToken(appId, tokenDomain.getRefreshToken(),
					"refresh_token");
			if (tokenResponse == null) {
				return null;
			}
			accessToken = tokenResponse.getAccessToken();

			tokenDomain.setAccessToken(accessToken);
			tokenDomain.setExpiresIn(tokenResponse.getExpiresIn());
			tokenDomain.setRefreshToken(tokenResponse.getRefreshToken());
			tokenDomain.setOpenId(tokenResponse.getOpenId());
			userAccessTokenService.saveOrUpdate(tokenDomain);
		}
		if (!StringUtils.isBlank(accessToken)) {
			GetUserInfoResponse userInfoResponse = this.wxEndpoint.getUserInfo(accessToken, openId, "zh_CN");
			return userInfoResponse;
		}
		return null;
	}

	@Override
	public GetUserInfoResponse getUserInfo(String appId, String appSecret, String code) {
		GetAccessTokenResponse tokenResponse = this.wxEndpoint.getAccessToken(appId, appSecret, code, "authorization_code");
		if (tokenResponse == null) {
			return null;
		}
		GetUserInfoResponse userInfoResponse = this.wxEndpoint.getUserInfo(tokenResponse.getAccessToken(), tokenResponse.getOpenId(),
				"zh_CN");
		return userInfoResponse;
	}

}
