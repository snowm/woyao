package com.woyao.wx;

import javax.annotation.Resource;
import javax.ws.rs.client.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//@PropertySource("classpath:wx.properties")
@Configuration
public class WxConfig {

	@Autowired
	private Environment env;

	@Resource(name = "wxJerseyClient")
	private Client client;

	@Bean(name = "wxEndpoint")
	public WxEndpoint wxEndpoint() {
		WxEndpoint t = new WxEndpoint();
		String getGlobalAccessTokenUrl = this.env.getProperty("wx.api.getGlobalAccessToken.url");
		String getAccessTokenUrl = this.env.getProperty("wx.api.getAccessToken.url");
		String refreshAccessTokenUrl = this.env.getProperty("wx.api.refreshAccessToken.url");
		String getUserInfoUrl = this.env.getProperty("wx.api.getUserInfo.url");
		t.setGetGlobalAccessTokenUrl(getGlobalAccessTokenUrl);
		t.setGetAccessTokenUrl(getAccessTokenUrl);
		t.setRefreshAccessTokenUrl(refreshAccessTokenUrl);
		t.setGetUserInfoUrl(getUserInfoUrl);
		t.setClient(this.client);
		return t;
	}
}
