package com.woyao.customer.queue;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class QueueConfig {

	@Autowired
	private Environment env;

	@Bean(name = "submitOrderBlockingQueue")
	public LinkedBlockingDeque<Long> submitOrderBlockingQueue() {
		String capacityStr = env.getProperty("queue.submitOrder.capacity", "10000");
		int capacity = 10000;
		try {
			capacity = Integer.parseInt(capacityStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LinkedBlockingDeque<Long> queue = new LinkedBlockingDeque<>(capacity);
		return queue;
	}

	@Bean(name = "submitOrderQueueService")
	public IOrderProcessQueue submitOrderQueueService() {
		IOrderProcessQueue queue = new OrderProcessQueueImpl(submitOrderBlockingQueue(), 5);
		return queue;
	}

}
