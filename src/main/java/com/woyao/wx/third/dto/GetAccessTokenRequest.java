package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAccessTokenRequest {

    @JsonProperty("component_appid")
	private String appId;

    @JsonProperty("component_appsecret")
	private String appSecret;

    @JsonProperty("component_verify_ticket")
	private String verifyTicket;
    
	public GetAccessTokenRequest(String appId, String appSecret, String verifyTicket) {
		super();
		this.appId = appId;
		this.appSecret = appSecret;
		this.verifyTicket = verifyTicket;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getVerifyTicket() {
		return verifyTicket;
	}

	public void setVerifyTicket(String verifyTicket) {
		this.verifyTicket = verifyTicket;
	}

}
