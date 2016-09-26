package com.woyao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:context-main-test.xml"})
public class SpringBaseCase {

	@Resource(name="sessionFactory")
	protected SessionFactory sessionFactory;

	@Resource(name="txManager")
	protected PlatformTransactionManager txManager;
	
	@Autowired
    private WebApplicationContext wac;

}
