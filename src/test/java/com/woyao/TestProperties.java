package com.woyao;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestProperties {

	@Test
	public void testSource(){
		try{
		System.setProperty("spring.profiles.active", "dev");
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("classpath:context-main.xml");
		context.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
