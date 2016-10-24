package com.woyao.customer.disruptor;

import com.lmax.disruptor.EventFactory;

public class ChatMsgEventFactory implements EventFactory<ChatMsgEvent> {

	@Override
	public ChatMsgEvent newInstance() {
		return new ChatMsgEvent();
	}

}
