package com.woyao.customer.disruptor;

import com.lmax.disruptor.RingBuffer;

public abstract class AbstractEventProducer<T,V> {

	protected final RingBuffer<T> ringBuffer;

	public AbstractEventProducer(RingBuffer<T> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public abstract void produce(V value);

}
