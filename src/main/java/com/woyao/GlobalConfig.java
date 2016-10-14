package com.woyao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("globalConfig")
public class GlobalConfig implements InitializingBean{

	@Value("${wx.verifyToken}")
	private String verifyToken;

	@Value("${wx.appId}")
	private String appId;

	@Value("${wx.appSecret}")
	private String appSecret;

	@Value("${wx.api.authorize.url}")
	private String authorizeUrl;

	@Value("${wx.api.authorize.paramFormat}")
	private String authorizeParamFormat;

	// https://{wxUrl}?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect
	private String authorizeFormat;

	@Value("${wx.pay.mchId}")
	private String mchId;

	@Value("${wx.pay.api.key}")
	private String payApiKey;

	@Value("${wx.api.pay.notify.url}")
	private String payNotifyUrl;
	
	// server env
	@Value("${server.host}")
	private String host;

	// 3rd party
	@Value("${wx.3rd.appId}")
	private String thirdAppId;

	@Value("${wx.3rd.msg.token}")
	private String thirdMsgToken;

	@Value("${wx.3rd.msg.encodingAesKey}")
	private String thirdEncodingAesKey;

	@Value("${earth.radius}")
	private double earthRadius;

	@Value("${earth.latitude.radius}")
	private double latitudeRadius;

	@Value("${earth.longitude.radius}")
	private double longitudeRadius;

	@Value("${shop.available.distance}")
	private double shopAvailableDistance;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.generateAuthorizeFormat();
	}
	
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
		this.authorizeFormat = this.authorizeUrl + "?" + this.authorizeParamFormat;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getPayApiKey() {
		return payApiKey;
	}

	public void setPayApiKey(String payApiKey) {
		this.payApiKey = payApiKey;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}
	
	// 3rd

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

	public double getEarthRadius() {
		return earthRadius;
	}

	public void setEarthRadius(double earthRadius) {
		this.earthRadius = earthRadius;
	}

	public double getLatitudeRadius() {
		return latitudeRadius;
	}

	public void setLatitudeRadius(double latitudeRadius) {
		this.latitudeRadius = latitudeRadius;
	}

	public double getLongitudeRadius() {
		return longitudeRadius;
	}

	public void setLongitudeRadius(double longitudeRadius) {
		this.longitudeRadius = longitudeRadius;
	}

	public double getShopAvailableDistance() {
		return shopAvailableDistance;
	}

	public void setShopAvailableDistance(double shopAvailableDistance) {
		this.shopAvailableDistance = shopAvailableDistance;
	}


}
