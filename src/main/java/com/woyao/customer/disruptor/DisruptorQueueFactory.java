package com.woyao.customer.disruptor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorQueueFactory<T> implements FactoryBean<Disruptor<T>>, InitializingBean, DisposableBean {

	private Disruptor<T> disruptor;

	private String threadName;

	private int ringBufferSize = 1024;

	private ProducerType producerType = ProducerType.MULTI;

	private WaitStrategy waitStrategy = new BlockingWaitStrategy();

	private EventFactory<T> eventFactory;

	private EventHandler<? super T>[] eventHandlers;

	@Override
	public void afterPropertiesSet() throws Exception {
		UncaughtExceptionHandler handler = new DisruptorUncaughtExceptionHandler();
		ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern(this.threadName + "--%s")
				.uncaughtExceptionHandler(handler).build();
		this.disruptor = new Disruptor<>(eventFactory, ringBufferSize, threadFactory, producerType, waitStrategy);
		this.disruptor.handleEventsWith(eventHandlers);
		java.util.logging.Logger logger = new com.woyao.log.Slf4jLogger("Disruptor-Handler", null);
		ExceptionHandler<Object> exceptionHandler = new IgnoreExceptionHandler(logger);
		this.disruptor.setDefaultExceptionHandler(exceptionHandler);
		
		this.disruptor.start();
	}

	@Override
	public Disruptor<T> getObject() throws Exception {
		return disruptor;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<Disruptor> getObjectType() {
		return Disruptor.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		this.disruptor.shutdown();
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public void setRingBufferSize(int ringBufferSize) {
		this.ringBufferSize = ringBufferSize;
	}

	public void setProducerType(ProducerType producerType) {
		this.producerType = producerType;
	}

	public void setWaitStrategy(WaitStrategy waitStrategy) {
		this.waitStrategy = waitStrategy;
	}

	public void setEventFactory(EventFactory<T> eventFactory) {
		this.eventFactory = eventFactory;
	}

	public void setEventHandlers(EventHandler<? super T>[] eventHandlers) {
		this.eventHandlers = eventHandlers;
	}


	private class DisruptorUncaughtExceptionHandler implements UncaughtExceptionHandler {

		private Logger logger = LoggerFactory.getLogger(this.getClass());

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			if (logger.isDebugEnabled()) {
				String msg = String.format("Disruptor thread %s terminated by uncaught exception!", t.getName());
				logger.debug(msg, e);
			}
		}
	}

}
