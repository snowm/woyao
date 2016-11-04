package com.woyao;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.woyao.customer.service.IProductService;

@Component("initComponents")
public class InitComponents implements InitializingBean {

	@Resource(name = "productService")
	private IProductService productService;

	@Override
	public void afterPropertiesSet() throws Exception {
		productService.loadMsgProductCache();
	}
}
