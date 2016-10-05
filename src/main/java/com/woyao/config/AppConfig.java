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

@Configuration("mainConfig")
@PropertySource(name = "mainProperty", value = "classpath:/config.properties")
@Import({ com.snowm.hibernate.ext.config.AppConfig.class, com.snowm.security.config.AppConfig.class, SecurityConfig.class })
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
//		globalConfig.setPrivateKey(this.env.getProperty("privateKey", "111111"));
//
//		globalConfig.setAliConnectionRequestTimeout(Integer.parseInt(this.env.getProperty("http.ali.ConnectionTimeout", "5000")));
//		globalConfig.setAliConnectionTimeout(Integer.parseInt(this.env.getProperty("http.ali.SocketTimeout", "10000")));
//		globalConfig.setAliConnectionRequestTimeout(Integer.parseInt(this.env.getProperty("http.ali.ConnectionRequestTimeout", "10000")));
//		globalConfig.setChannelTimeout(Integer.parseInt(this.env.getProperty("channel.callback.timeout", "600")));
		return globalConfig;
	}

}