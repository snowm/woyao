package com.woyao;

import javax.annotation.Resource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.woyao.customer.queue.SubmitOrderListenTask;

@Component("initComponents")
public class InitComponents implements InitializingBean, DisposableBean {

	@Resource(name = "submitOrderListenTask")
	private SubmitOrderListenTask submitOrderListenTask;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.submitOrderListenTask.start();
	}

	@Override
	public void destroy() throws Exception {
		this.submitOrderListenTask.destroy();
	}
}
