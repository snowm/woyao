package com.woyao.config;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.snowm.hibernate.ext.multi.SwitchableSessionFactoryWrapper;
import com.snowm.hibernate.ext.multi.SwitchableTransactionManagerWrapper;

@Configuration("mainConfig")
@Import({ com.snowm.hibernate.ext.config.AppConfig.class, com.snowm.security.config.AppConfig.class})
@ComponentScan({ "com.snowm", "com.woyao" })
public class AppConfig {

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

}