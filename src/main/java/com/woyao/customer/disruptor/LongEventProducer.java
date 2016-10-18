package com.woyao.customer.disruptor;

import com.lmax.disruptor.RingBuffer;

public class LongEventProducer extends AbstractEventProducer<LongEvent, Long> {

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		super(ringBuffer);
	}

	public void produce(Long value) {
		RingBuffer<LongEvent> rb = this.ringBuffer;
		long sequence = rb.next();
		try {
			LongEvent event = rb.get(sequence);
			event.set(value);
		} finally {
			rb.publish(sequence);
		}
	}

}
