package com.woyao.customer.queue;

public interface IOrderProcessQueue {

	boolean putToQueue(Long orderId);

	boolean putToQueueWithHighPriority(Long orderId);

	long getFromQueue();

}
