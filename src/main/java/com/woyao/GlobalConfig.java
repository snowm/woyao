package com.woyao;

public class GlobalConfig {

	private String verifyToken;

	private String appId;

	private String appSecret;

	private String authorizeUrl;

	private String authorizeParamFormat;

	// https://{wxUrl}?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect
	private String authorizeFormat;

	private String thirdAppId;

	private String thirdMsgToken;

	private String thirdEncodingAesKey;

	public String getVerifyToken() {
		return verifyToken;
	}

	public void setVerifyToken(String verifyToken) {
		this.verifyToken = verifyToken;
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

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public void setAuthorizeUrl(String authorizeUrl) {
		this.authorizeUrl = authorizeUrl;
	}

	public String getAuthorizeParamFormat() {
		return authorizeParamFormat;
	}

	public void setAuthorizeParamFormat(String authorizeParamFormat) {
		this.authorizeParamFormat = authorizeParamFormat;
	}

	public String getAuthorizeFormat() {
		return authorizeFormat;
	}

	public void generateAuthorizeFormat() {
		this.authorizeFormat = this.authorizeUrl + "?" + this.authorizeFormat;
	}

	public String getThirdAppId() {
		return thirdAppId;
	}

	public void setThirdAppId(String thirdAppId) {
		this.thirdAppId = thirdAppId;
	}

	public String getThirdMsgToken() {
		return thirdMsgToken;
	}

	public void setThirdMsgToken(String thirdMsgToken) {
		this.thirdMsgToken = thirdMsgToken;
	}

	public String getThirdEncodingAesKey() {
		return thirdEncodingAesKey;
	}

	public void setThirdEncodingAesKey(String thirdEncodingAesKey) {
		this.thirdEncodingAesKey = thirdEncodingAesKey;
	}

}
