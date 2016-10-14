package com.woyao.customer.queue;

import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component("submitOrderListenTask")
public class SubmitOrderListenTask extends AbstractListenerTask {

	private Log log = LogFactory.getLog(this.getClass());
	
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
		if (log.isDebugEnabled()) {
			log.debug("received submit order message:" + localOrderId);
		}
		ConsumeTask task = new ConsumeTask(consumer, localOrderId);
		return task;
	}

	private class ConsumeTask implements Callable<Boolean> {

		private Log log = LogFactory.getLog(this.getClass());
		private SubmitOrderMessageConsumer consumer;
		private long localOrderId;

		public ConsumeTask(SubmitOrderMessageConsumer consumer, long localOrderId) {
			super();
			this.consumer = consumer;
			this.localOrderId = localOrderId;
		}

		public Boolean call() throws Exception {
			try {
				if (log.isDebugEnabled()) {
					log.debug("Starting to consume submit order message:" + this.localOrderId);
				}
				this.consumer.consume(localOrderId);
				return true;
			} catch (Exception ex) {
				if (log.isWarnEnabled()) {
					log.warn("consume order error!", ex);
				}
			}
			return false;
		}

	}

}
