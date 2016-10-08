package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetPreAuthCodeRequest {

	@JsonProperty("component_appid")
	private String appId;

	public GetPreAuthCodeRequest(String appId) {
		super();
		this.appId = appId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
