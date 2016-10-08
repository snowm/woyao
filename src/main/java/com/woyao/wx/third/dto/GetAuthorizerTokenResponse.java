package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAuthorizerTokenResponse {

	@JsonProperty("authorizer_access_token")
	private String authorizerAccessToken;

	@JsonProperty("expires_in")
	private Long expiresIn;

	@JsonProperty("authorizer_refresh_token")
	private String authorizerRefreshToken;

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

}
