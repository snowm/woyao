package com.woyao.customer.queue;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.woyao.customer.disruptor.DisruptorQueueFactory;
import com.woyao.customer.disruptor.LongEvent;
import com.woyao.customer.disruptor.LongEventFactory;
import com.woyao.customer.disruptor.LongEventProducer;
import com.woyao.customer.disruptor.SubmitOrderMessageHandler;

@Configuration
public class QueueConfig {

	// @Bean(name = "submitOrderBlockingQueue")
	// public LinkedBlockingDeque<Long>
	// submitOrderBlockingQueue(@Value("${queue.submitOrder.capacity}") int
	// capacity) {
	// LinkedBlockingDeque<Long> queue = new LinkedBlockingDeque<>(capacity);
	// return queue;
	// }
	//
	// @Bean(name = "submitOrderQueueService")
	// public IOrderProcessQueue submitOrderQueueService(
	// @Qualifier("submitOrderBlockingQueue") LinkedBlockingDeque<Long>
	// submitOrderBlockingQueue) {
	// IOrderProcessQueue queue = new
	// OrderProcessQueueImpl(submitOrderBlockingQueue, 5);
	// return queue;
	// }

	@Bean(name = "longEventFactory")
	public EventFactory<LongEvent> longEventFactory() {
		return new LongEventFactory();
	}

	@Bean(name = "submitOrderDisruptor")
	public DisruptorQueueFactory<LongEvent> submitOrderDisruptor(@Qualifier("longEventFactory") EventFactory<LongEvent> eventFactory,
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
}
