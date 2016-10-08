package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizeRequest {

	@JsonProperty("component_appid")
	private String appId;

	@JsonProperty("authorization_code")
	private String authorizationCode;

	public AuthorizeRequest(String appId, String authorizationCode) {
		super();
		this.appId = appId;
		this.authorizationCode = authorizationCode;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

}
