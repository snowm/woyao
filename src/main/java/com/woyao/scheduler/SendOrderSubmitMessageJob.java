package com.woyao.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.woyao.customer.queue.IOrderProcessQueue;
import com.woyao.customer.service.IOrderService;

@Component
public class SendOrderSubmitMessageJob {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "submitOrderQueueService")
	private IOrderProcessQueue submitOrderQueueService;

	private int maxSize = 300;

	@Scheduled(fixedDelay = 120000, initialDelay = 60000)
	public void executeInternal() {
		logger.debug("Starting to queue missed order...");
		long start = System.currentTimeMillis();
		List<Long> localOrderIds = this.orderService.queryUnSubmittedOrders(maxSize);
		int size = localOrderIds.size();
		logger.debug("{} unSubmitted order found!", size);
		int i = 0;
		for (Long id : localOrderIds) {
			if (this.submitOrderQueueService.putToQueue(id)) {
				i++;
			}
		}
		if (logger.isDebugEnabled()) {
			long spent = System.currentTimeMillis() - start;
			logger.debug("Put " + i + " orders into queue! Spent time:" + spent + " ms");
		}
	}
}
