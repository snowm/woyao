package com.woyao.wx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGlobalAccessTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private Long expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

}
