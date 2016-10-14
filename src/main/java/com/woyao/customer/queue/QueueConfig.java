package com.woyao.customer.queue;

import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	@Bean(name = "submitOrderBlockingQueue")
	public LinkedBlockingDeque<Long> submitOrderBlockingQueue(@Value("${queue.submitOrder.capacity}") int capacity) {
		LinkedBlockingDeque<Long> queue = new LinkedBlockingDeque<>(capacity);
		return queue;
	}

	@Bean(name = "submitOrderQueueService")
	public IOrderProcessQueue submitOrderQueueService(
			@Qualifier("submitOrderBlockingQueue") LinkedBlockingDeque<Long> submitOrderBlockingQueue) {
		IOrderProcessQueue queue = new OrderProcessQueueImpl(submitOrderBlockingQueue, 5);
		return queue;
	}

}
