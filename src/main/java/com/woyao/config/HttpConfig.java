package com.woyao.config;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.woyao.jersey.JerseyApacheClientFactory;
import com.woyao.jersey.JerseyNettyClientFactory;

public class HttpConfig {

	@Autowired
	private Environment env;

	@Bean(name = "httpConnManager")
	public HttpClientConnectionManager connectionManager() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultMaxPerRoute(this.env.getProperty("http.client.conn.manager.defaultMaxPerRoute", Integer.class));
		connectionManager.setMaxTotal(this.env.getProperty("http.client.conn.manager.maxTotal", Integer.class));
		return connectionManager;
	}

	@Bean(name = "wxJerseyApacheClientFactory")
	public JerseyApacheClientFactory wxJerseyApacheClientFactory() {
		JerseyApacheClientFactory cf = new JerseyApacheClientFactory();
		cf.setConnectTimeout(this.env.getProperty("wx.conn.connectTimeout", Integer.class));
		cf.setSocketTimeout(this.env.getProperty("wx.conn.socketTimeout", Integer.class));
		cf.setConnectionRequestTimeout(this.env.getProperty("wx.conn.connectionRequestTimeout", Integer.class));
		cf.setMaxIdleTime(this.env.getProperty("wx.conn.maxIdleTime", Long.class));
		cf.setMaxIdleTimeUnit(this.env.getProperty("wx.conn.maxIdleTimeUnit", TimeUnit.class));
		cf.setConnManager(this.connectionManager());
		return cf;
	}
	
	@Bean(name = "wxJerseyNettyClientFactory")
	public JerseyNettyClientFactory wxJerseyNettyClientFactory() {
		JerseyNettyClientFactory cf = new JerseyNettyClientFactory();
		cf.setConnectTimeout(this.env.getProperty("wx.conn.connectTimeout", Integer.class));
		cf.setSocketTimeout(this.env.getProperty("wx.conn.socketTimeout", Integer.class));
		return cf;
	}

	@Bean(name = "wxJerseyClient")
	public Client wxJerseyClient() throws Exception {
		Client client = this.wxJerseyNettyClientFactory().getObject();
		return client;
	}
}
