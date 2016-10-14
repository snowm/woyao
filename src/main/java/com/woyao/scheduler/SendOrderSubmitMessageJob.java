package com.woyao.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.woyao.customer.queue.IOrderProcessQueue;
import com.woyao.customer.service.IOrderService;

@Component
public class SendOrderSubmitMessageJob {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "submitOrderQueueService")
	private IOrderProcessQueue submitOrderQueueService;

	private int maxSize = 300;
	
	@Scheduled(fixedDelay = 120000, initialDelay = 60000)
	public void executeInternal() {
		if (this.log.isDebugEnabled()) {
			this.log.debug("Starting to queue missed order...");
		}
		long start = System.currentTimeMillis();
		List<Long> localOrderIds = this.orderService.queryUnSubmittedOrders(maxSize);
		int size = localOrderIds.size();
		if (this.log.isDebugEnabled()) {
			this.log.debug(size + " un submitted order found!");
		}
		int i = 0;
		for (Long id : localOrderIds) {
			if (this.submitOrderQueueService.putToQueue(id)) {
				i++;
			}
		}
		if (this.log.isDebugEnabled()) {
			long spent = System.currentTimeMillis() - start;
			this.log.debug("Put " + i + " orders into queue! Spent time:" + spent + " ms");
		}
	}
}
