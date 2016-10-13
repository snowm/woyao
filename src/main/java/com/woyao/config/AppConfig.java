package com.woyao.config;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;

import com.snowm.hibernate.ext.multi.SwitchableSessionFactoryWrapper;
import com.snowm.hibernate.ext.multi.SwitchableTransactionManagerWrapper;
import com.woyao.GlobalConfig;
import com.woyao.wx.WxConfig;

@Configuration("mainConfig")
@PropertySource(name = "mainProperty", value = { "classpath:/config.properties", "classpath:/wx.properties" })
@Import({ com.snowm.hibernate.ext.config.AppConfig.class, com.snowm.security.config.AppConfig.class, HttpConfig.class, WxConfig.class })
@ComponentScan({ "com.snowm", "com.woyao" })
// @Profile("dev")
public class AppConfig {

	@Autowired
	private Environment env;

	@Resource(name = "originalSessionFactory")
	private SessionFactory originalSessionFactory;

	@Resource(name = "originalTxManager")
	private PlatformTransactionManager originalTransactionManager;

	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory() {
		SwitchableSessionFactoryWrapper sessionFactory = new SwitchableSessionFactoryWrapper();
		sessionFactory.setDefaultSessionFactory(this.originalSessionFactory);
		return sessionFactory;
	}

	@Bean(name = "commonSessionFactory")
	public SessionFactory commonSessionFactory() {
		return this.sessionFactory();
	}

	@Bean(name = "securitySessionFactory")
	public SessionFactory securitySessionFactory() {
		return this.sessionFactory();
	}

	@Bean(name = "txManager")
	public PlatformTransactionManager txManager() {
		SwitchableTransactionManagerWrapper txManager = new SwitchableTransactionManagerWrapper();
		txManager.setDefaultTxManager(originalTransactionManager);
		return txManager;
	}

	@Bean(name = "commonTxManager")
	public PlatformTransactionManager commonTxManager() {
		return this.txManager();
	}

	@Bean(name = "securityTxManager")
	public PlatformTransactionManager securityTxManager() {
		return this.txManager();
	}

	@Bean(name = "globalConfig")
	public GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setVerifyToken(this.env.getProperty("wx.verifyToken", "test"));
		globalConfig.setAppId(this.env.getProperty("wx.appId", "test"));
		globalConfig.setAppSecret(this.env.getProperty("wx.appSecret", "test"));
		globalConfig.setAuthorizeUrl(this.env.getProperty("wx.api.authorize.url"));
		globalConfig.setAuthorizeParamFormat(this.env.getProperty("wx.api.authorize.paramFormat"));
		globalConfig.generateAuthorizeFormat();
		globalConfig.setMrchId(this.env.getProperty("wx.pay.mrchId"));

		globalConfig.setThirdMsgToken(this.env.getProperty("wx.3rd.msg.token", "test1"));
		globalConfig
				.setThirdEncodingAesKey(this.env.getProperty("wx.3rd.msg.encodingAesKey", "kjADF9IKj1iuwe98237k23972j9097ca91MNie83176"));
		globalConfig.setThirdAppId(this.env.getProperty("wx.3rd.appId", "wxc72bd2fb81d08c06"));

		globalConfig.setEarthRadius(this.env.getProperty("earth.radius", double.class));
		globalConfig.setLatitudeRadius(this.env.getProperty("latitude.radius", double.class));
		globalConfig.setLongitudeRadius(this.env.getProperty("longitude.radius", double.class));
		globalConfig.setShopAvailableDistance(this.env.getProperty("shop.available.distance", double.class));

		return globalConfig;
	}

}