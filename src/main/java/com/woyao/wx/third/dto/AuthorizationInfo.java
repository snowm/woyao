package com.woyao.wx.third.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationInfo {

	@JsonProperty("appId")
	private String appId;

	@JsonProperty("authorizer_appid")
	private String authorizeAppId;

	@JsonProperty("authorizer_access_token")
	private String authorizerAccessToken;

	@JsonProperty("expires_in")
	private Long expiresIn;

	@JsonProperty("authorizer_refresh_token")
	private String authorizerRefreshToken;

	@JsonProperty("func_info")
	private List<FunctionInfo> funcInfos;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
		this.setAuthorizeAppId(appId);
	}

	public String getAuthorizeAppId() {
		return authorizeAppId;
	}

	public void setAuthorizeAppId(String authorizeAppId) {
		this.authorizeAppId = authorizeAppId;
		this.setAppId(authorizeAppId);
	}

	public String getAuthorizerAccessToken() {
		return authorizerAccessToken;
	}

	public void setAuthorizerAccessToken(String authorizerAccessToken) {
		this.authorizerAccessToken = authorizerAccessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

	public List<FunctionInfo> getFuncInfos() {
		return funcInfos;
	}

	public void setFuncInfos(List<FunctionInfo> funcInfos) {
		this.funcInfos = funcInfos;
	}

}
