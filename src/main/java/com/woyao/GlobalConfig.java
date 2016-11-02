package com.woyao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("globalConfig")
public class GlobalConfig implements InitializingBean {

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

	@Value("${server.root.context}")
	private String rootContext;
	
	@Value("${server.root.url}")
	private String rootUrl;

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

	@Value("${env}")
	private String env;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.generateAuthorizeFormat();
	}

	public String getVerifyToken() {
		return verifyToken;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getAuthorizeUrl() {
		return authorizeUrl;
	}

	public String getAuthorizeParamFormat() {
		return authorizeParamFormat;
	}

	public String getAuthorizeFormat() {
		return authorizeFormat;
	}

	public String getMchId() {
		return mchId;
	}

	public String getPayApiKey() {
		return payApiKey;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public String getHost() {
		return host;
	}

	public String getRootContext() {
		return rootContext;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public String getThirdAppId() {
		return thirdAppId;
	}

	public String getThirdMsgToken() {
		return thirdMsgToken;
	}

	public String getThirdEncodingAesKey() {
		return thirdEncodingAesKey;
	}

	public double getEarthRadius() {
		return earthRadius;
	}

	public double getLatitudeRadius() {
		return latitudeRadius;
	}

	public double getLongitudeRadius() {
		return longitudeRadius;
	}

	public double getShopAvailableDistance() {
		return shopAvailableDistance;
	}

	private void generateAuthorizeFormat() {
		this.authorizeFormat = this.authorizeUrl + "?" + this.authorizeParamFormat;
	}

	public String getEnv() {
		return env;
	}

}
