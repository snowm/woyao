package com.woyao.customer.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.woyao.customer.dto.chat.out.Outbound;

public class ChatMsgEventProducer {

	private final RingBuffer<ChatMsgEvent> ringBuffer;

	public ChatMsgEventProducer(RingBuffer<ChatMsgEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void produce(String toSessionId, Outbound outbound) {
		RingBuffer<ChatMsgEvent> rb = this.ringBuffer;
		long sequence = rb.next();
		try {
			ChatMsgEvent event = rb.get(sequence);
			event.setOutbound(outbound);
			event.setToSessionId(toSessionId);
		} finally {
			rb.publish(sequence);
		}
	}

}
