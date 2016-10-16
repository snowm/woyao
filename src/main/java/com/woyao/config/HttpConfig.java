package com.woyao.config;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woyao.jersey.JerseyApacheClientFactory;
import com.woyao.jersey.JerseyNettyClientFactory;

@Configuration
public class HttpConfig {

	@Bean(name = "httpConnManager")
	public HttpClientConnectionManager connectionManager(
			@Value("${http.client.conn.manager.defaultMaxPerRoute}") int defaultMaxPerRoute,
			@Value("${http.client.conn.manager.maxTotal}") int maxTotal) {
		
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
		connectionManager.setMaxTotal(maxTotal);
		return connectionManager;
	}

	@Bean(name = "wxJerseyApacheClient")
	public JerseyApacheClientFactory wxJerseyApacheClientFactory(
			@Qualifier("httpConnManager") HttpClientConnectionManager connManager,
			@Value("${wx.conn.connectTimeout}") int connectTimeout, 
			@Value("${wx.conn.socketTimeout}") int socketTimeout,
			@Value("${wx.conn.connectionRequestTimeout}") int connectionRequestTimeout, 
			@Value("${wx.conn.maxIdleTime}") long maxIdleTime,
			@Value("${wx.conn.maxIdleTimeUnit}") TimeUnit maxIdleTimeUnit) {
		
		JerseyApacheClientFactory cf = new JerseyApacheClientFactory();
		cf.setConnectTimeout(connectTimeout);
		cf.setSocketTimeout(socketTimeout);
		cf.setConnectionRequestTimeout(connectionRequestTimeout);
		cf.setMaxIdleTime(maxIdleTime);
		cf.setMaxIdleTimeUnit(maxIdleTimeUnit);
		cf.setConnManager(connManager);
		return cf;
	}

	@Bean(name = "wxJerseyClient")
	public JerseyNettyClientFactory wxJerseyNettyClientFactory(
			@Value("${wx.conn.connectTimeout}") int connectTimeout, 
			@Value("${wx.conn.socketTimeout}") int socketTimeout, 
			@Value("${wx.conn.asyncThreads}") int asyncThreadPoolSize) {
		
		JerseyNettyClientFactory cf = new JerseyNettyClientFactory();
		cf.setAsyncThreadPoolSize(asyncThreadPoolSize);
		cf.setConnectTimeout(connectTimeout);
		cf.setSocketTimeout(socketTimeout);
		return cf;
	}

}
