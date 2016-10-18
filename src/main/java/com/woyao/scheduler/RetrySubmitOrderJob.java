package com.woyao.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.woyao.customer.disruptor.LongEventProducer;
import com.woyao.customer.service.IOrderService;
import com.woyao.utils.TimeLogger;

@Component
public class RetrySubmitOrderJob {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "orderService")
	private IOrderService orderService;

	// @Resource(name = "submitOrderQueueService")
	// private IOrderProcessQueue submitOrderQueueService;

	@Resource(name = "submitOrderProducer")
	private LongEventProducer submitOrderProducer;

	@Value("${submitOrder.retry.task.batchSize}")
	private int batchSize;

	@Scheduled(fixedDelay = 120 * 1000, initialDelay = 60 * 1000)
	public void executeInternal() {
		logger.debug("Starting to queue missed order...");
		TimeLogger tl = null;
		if (logger.isDebugEnabled()) {
			tl = TimeLogger.newLogger().start();
		}
		try {
			List<Long> orderIds = this.orderService.queryUnSubmittedOrders(batchSize);
			int size = orderIds.size();
			logger.debug("{} unSubmitted order found!", size);
			int i = 0;
			LongEventProducer producer = this.submitOrderProducer;
			for (Long id : orderIds) {
				producer.produce(id);
				i++;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Put {} orders into queue! Spent time:{} ms.", i, tl.end().spent());
			}
		} catch (Exception ex) {
			logger.error("Retry to submit order failure!", ex);
		}
	}
}
