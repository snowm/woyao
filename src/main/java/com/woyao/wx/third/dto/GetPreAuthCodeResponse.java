package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetPreAuthCodeResponse {

	@JsonProperty("pre_auth_code")
	private String preAuthCode;

	@JsonProperty("expires_in")
	private Long expiresIn;

	public String getPreAuthCode() {
		return preAuthCode;
	}

	public void setPreAuthCode(String preAuthCode) {
		this.preAuthCode = preAuthCode;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

}
