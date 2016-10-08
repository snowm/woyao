package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAuthorizerTokenRequest {

	@JsonProperty("component_appid")
	private String appId;

	@JsonProperty("authorizer_appid")
	private String authorizerAppid;

	@JsonProperty("authorizer_refresh_token")
	private String authorizerRefreshToken;

	public GetAuthorizerTokenRequest(String appId, String authorizerAppid, String authorizerRefreshToken) {
		super();
		this.appId = appId;
		this.authorizerAppid = authorizerAppid;
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAuthorizerAppid() {
		return authorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		this.authorizerAppid = authorizerAppid;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

}
