package com.woyao.customer.queue;

import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("submitOrderListenTask")
public class SubmitOrderListenTask extends AbstractListenerTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "submitOrderQueueService")
	private IOrderProcessQueue queue;

	@Resource(name = "submitOrderMessageConsumer")
	private SubmitOrderMessageConsumer consumer;

	@Override
	protected IOrderProcessQueue getQueue() {
		return queue;
	}

	@Override
	protected Callable<?> createTask(long localOrderId) {
		logger.debug("received submit order message:{}", localOrderId);
		ConsumeTask task = new ConsumeTask(consumer, localOrderId);
		return task;
	}

	private class ConsumeTask implements Callable<Boolean> {

		private Logger logger = LoggerFactory.getLogger(this.getClass());

		private SubmitOrderMessageConsumer consumer;

		private long localOrderId;

		public ConsumeTask(SubmitOrderMessageConsumer consumer, long localOrderId) {
			super();
			this.consumer = consumer;
			this.localOrderId = localOrderId;
		}

		public Boolean call() throws Exception {
			try {
				logger.debug("Starting to consume submit order message:{}", this.localOrderId);
				this.consumer.consume(localOrderId);
				return true;
			} catch (Exception ex) {
				logger.warn("consume order error!", ex);
			}
			return false;
		}

	}

}
