package com.woyao.customer.disruptor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;

public abstract class AbstractEventHandler<T> implements EventHandler<T>, LifecycleAware, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private int threads = 10;

	private ExecutorService es;

	private boolean destroyed = false;

	private long taskTimeout = 10L;

	private TimeUnit taskTimeoutTimeUnit = TimeUnit.SECONDS;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.es = Executors.newFixedThreadPool(threads);
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onEvent(T event, long sequence, boolean endOfBatch) throws Exception {
		if (this.destroyed) {
			throw new IllegalStateException("Handler is destroyed!");
		}
		Callable<?> task = this.createTask(event, sequence, endOfBatch);
		Future<?> future = this.es.submit(task);
		future.get(taskTimeout, taskTimeoutTimeUnit);
	}

	@Override
	public void onShutdown() {
		logger.debug("Shut down handler!");
		this.destroyed = true;
		if (!this.es.isShutdown()) {
			this.es.shutdown();
		}
		try {
			this.es.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException ex) {
			logger.error("Shut down is interrupted!", ex);
		}
		logger.debug("Handler is down!");
	}

	protected abstract void doTask(T event, long sequence, boolean endOfBatch);

	private Callable<?> createTask(T event, long sequence, boolean endOfBatch) {
		return new HandleEventTask(this, event, sequence, endOfBatch);
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public long getTaskTimeout() {
		return taskTimeout;
	}

	public void setTaskTimeout(long taskTimeout) {
		this.taskTimeout = taskTimeout;
	}

	public TimeUnit getTaskTimeoutTimeUnit() {
		return taskTimeoutTimeUnit;
	}

	public void setTaskTimeoutTimeUnit(TimeUnit taskTimeoutTimeUnit) {
		this.taskTimeoutTimeUnit = taskTimeoutTimeUnit;
	}

	private class HandleEventTask implements Callable<Boolean> {

		private AbstractEventHandler<T> handler;
		private T event;
		private long sequence;
		private boolean endOfBatch;

		private HandleEventTask(AbstractEventHandler<T> handler, T event, long sequence, boolean endOfBatch) {
			super();
			this.handler = handler;
			this.event = event;
			this.sequence = sequence;
			this.endOfBatch = endOfBatch;
		}

		@Override
		public Boolean call() throws Exception {
			this.handler.doTask(event, sequence, endOfBatch);
			return true;
		}

	}
}
