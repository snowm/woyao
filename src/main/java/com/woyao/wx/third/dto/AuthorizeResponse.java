package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizeResponse {

	@JsonProperty("authorization_info")
	private AuthorizationInfo info;

	public AuthorizationInfo getInfo() {
		return info;
	}

	public void setInfo(AuthorizationInfo info) {
		this.info = info;
	}

}
