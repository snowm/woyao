package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAuthorizerInfoResponse {

	@JsonProperty("authorizer_info")
	private AuthorizerInfo authorizerInfo;

	@JsonProperty("qrcode_url")
	private String qrcodeUrl;

	@JsonProperty("authorization_info")
	private AuthorizationInfo authorizationInfo;

	public AuthorizerInfo getAuthorizerInfo() {
		return authorizerInfo;
	}

	public void setAuthorizerInfo(AuthorizerInfo authorizerInfo) {
		this.authorizerInfo = authorizerInfo;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public AuthorizationInfo getAuthorizationInfo() {
		return authorizationInfo;
	}

	public void setAuthorizationInfo(AuthorizationInfo authorizationInfo) {
		this.authorizationInfo = authorizationInfo;
	}

}
