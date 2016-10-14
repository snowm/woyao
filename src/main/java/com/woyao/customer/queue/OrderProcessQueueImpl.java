package com.woyao.customer.queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OrderProcessQueueImpl implements IOrderProcessQueue {

	private Log log = LogFactory.getLog(OrderProcessQueueImpl.class);

	private LinkedBlockingDeque<Long> queue;

	private int offerTimeout = 1;

	public OrderProcessQueueImpl(LinkedBlockingDeque<Long> queue) {
		super();
		this.queue = queue;
	}

	public OrderProcessQueueImpl(LinkedBlockingDeque<Long> queue, int offerTimeout) {
		super();
		this.queue = queue;
		this.offerTimeout = offerTimeout;
	}

	@Override
	public boolean putToQueue(Long orderId) {
		try {
			return queue.offerLast(orderId, offerTimeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("Blocking queue is interrupted!", e);
			return false;
		}
	}

	@Override
	public boolean putToQueueWithHighPriority(Long orderId) {
		try {
			return queue.offerFirst(orderId, offerTimeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("Blocking queue is interrupted!", e);
		}
		return false;
	}

	@Override
	public long getFromQueue() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			log.error("Blocking queue is interrupted!", e);
		}
		return 0;
	}

	public int getOfferTimeout() {
		return offerTimeout;
	}

	public void setOfferTimeout(int offerTimeout) {
		this.offerTimeout = offerTimeout;
	}

}
