package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAuthorizerInfoRequest {

	@JsonProperty("component_appid")
	private String appId;

	@JsonProperty("authorizer_appid")
	private String authorizerAppid;


	public GetAuthorizerInfoRequest(String appId, String authorizerAppid) {
		super();
		this.appId = appId;
		this.authorizerAppid = authorizerAppid;
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

}
