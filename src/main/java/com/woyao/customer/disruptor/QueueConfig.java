package com.woyao.customer.disruptor;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

@Configuration
public class QueueConfig {

	@Bean(name = "longEventFactory")
	public EventFactory<LongEvent> longEventFactory() {
		return new LongEventFactory();
	}

	@Bean(name = "submitOrderDisruptor")
	public DisruptorQueueFactory<LongEvent> submitOrderDisruptor(
			@Qualifier("longEventFactory") EventFactory<LongEvent> eventFactory,
			@Value("${submitOrder.disruptor.bufferSize}") int ringBufferSize,
			@Qualifier("submitOrderMessageHandler") EventHandler<LongEvent> submitOrderMessageHandler) {
		DisruptorQueueFactory<LongEvent> disruptorFactory = new DisruptorQueueFactory<>();
		@SuppressWarnings("unchecked")
		EventHandler<? super LongEvent>[] eventHandlers = new EventHandler[] { submitOrderMessageHandler };
		disruptorFactory.setEventFactory(eventFactory);
		disruptorFactory.setEventHandlers(eventHandlers);
		disruptorFactory.setRingBufferSize(ringBufferSize);
		disruptorFactory.setThreadName("disruptor-submitOrder");
		return disruptorFactory;
	}

	@Bean(name = "submitOrderProducer")
	public LongEventProducer submitOrderProducer(@Qualifier("submitOrderDisruptor") Disruptor<LongEvent> disruptor) {
		LongEventProducer producer = new LongEventProducer(disruptor.getRingBuffer());
		return producer;
	}

	@Bean(name = "submitOrderMessageHandler")
	public SubmitOrderMessageHandler submitOrderMessageHandler(
			@Value("${submitOrder.disruptor.handler.threads}") int threads,
			@Value("${submitOrder.disruptor.handler.taskTimeout}") long taskTimeout,
			@Value("${submitOrder.disruptor.handler.taskTimeoutTimeunit}") TimeUnit taskTimeoutTimeUnit) {
		SubmitOrderMessageHandler handler = new SubmitOrderMessageHandler();
		handler.setThreads(threads);
		handler.setTaskTimeout(taskTimeout);
		handler.setTaskTimeoutTimeUnit(taskTimeoutTimeUnit);
		return handler;
	}
	
	
	

	@Bean(name = "chatMsgEventFactory")
	public EventFactory<ChatMsgEvent> roomMsgEventFactory() {
		return new ChatMsgEventFactory();
	}

	@Bean(name = "chatMsgEventHandler")
	public ChatMsgEventHandler chatMsgEventHandler(
			@Value("${chatMsg.disruptor.handler.threads}") int threads,
			@Value("${chatMsg.disruptor.handler.taskTimeout}") long taskTimeout,
			@Value("${chatMsg.disruptor.handler.taskTimeoutTimeunit}") TimeUnit taskTimeoutTimeUnit) {
		ChatMsgEventHandler handler = new ChatMsgEventHandler();
		handler.setTaskTimeout(taskTimeout);
		handler.setTaskTimeoutTimeUnit(taskTimeoutTimeUnit);
		handler.setThreads(threads);
		return handler;
	}

	@Bean(name = "chatMsgDisruptor")
	public DisruptorQueueFactory<ChatMsgEvent> roomMsgDisruptor(
			@Qualifier("chatMsgEventFactory") EventFactory<ChatMsgEvent> eventFactory,
			@Value("${chatMsg.disruptor.bufferSize}") int ringBufferSize,
			@Qualifier("chatMsgEventHandler") EventHandler<ChatMsgEvent> chatMsgHandler) {
		DisruptorQueueFactory<ChatMsgEvent> disruptorFactory = new DisruptorQueueFactory<>();
		@SuppressWarnings("unchecked")
		EventHandler<? super ChatMsgEvent>[] eventHandlers = new EventHandler[] { chatMsgHandler };
		disruptorFactory.setEventFactory(eventFactory);
		disruptorFactory.setEventHandlers(eventHandlers);
		disruptorFactory.setRingBufferSize(ringBufferSize);
		disruptorFactory.setThreadName("disruptor-chatMsg");
		return disruptorFactory;
	}

	@Bean(name = "chatMsgEventProducer")
	public ChatMsgEventProducer chatMsgEventProducer(@Qualifier("chatMsgDisruptor") Disruptor<ChatMsgEvent> disruptor) {
		ChatMsgEventProducer producer = new ChatMsgEventProducer(disruptor.getRingBuffer());
		return producer;
	}

}
