package com.woyao.customer.queue;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractListenerTask implements Runnable {

	private Log log = LogFactory.getLog(this.getClass());

	@Value("${queue.submitOrder.consumer.threads}")
	private int submitOrderConsumerThreads;

	private ExecutorService es;

	private boolean stop = false;

	private List<Future<?>> futures = new CopyOnWriteArrayList<>();

	private AtomicBoolean started = new AtomicBoolean(false);

	private Thread t = null;

	@Override
	public void run() {
		int size = submitOrderConsumerThreads;
		this.es = Executors.newFixedThreadPool(size);
		int i = 0;
		while (!stop) {
			try {
				if (++i >= 100) {
					i = 0;
					this.clearDoneCancelledFuture();
				}
				long localOrderId = this.getQueue().getFromQueue();
				Callable<?> task = this.createTask(localOrderId);
				Future<?> future = this.es.submit(task);
				this.futures.add(future);
			} catch (Exception ex) {
				if (log.isWarnEnabled()) {
					log.warn("consume order error!", ex);
				}
			}
		}
	}

	protected abstract IOrderProcessQueue getQueue();
	protected abstract Callable<?> createTask(long localOrderId);

	public void start() {
		if (!started.getAndSet(true)) {
			log.debug("Start submit order listerner!");
			this.t = new Thread(this, "submitOrderListenTask");
			this.t.start();
		}
	}

	@PreDestroy
	public void destroy() {
		this.stop();
		Iterator<Future<?>> itr = this.futures.iterator();
		while (itr.hasNext()) {
			Future<?> f = itr.next();
			if (f.isDone() || f.isCancelled()) {
				itr.remove();
			} else {
				f.cancel(true);
				itr.remove();
			}
		}
	}

	public void stop() {
		log.debug("stopping submit order listener!");
		this.stop = true;
		if (!this.es.isShutdown()) {
			this.es.shutdown();
		}
		this.t.interrupt();
		try {
			this.es.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException ex) {
			log.error("submit order listen task shut down is interrupted!", ex);
		}
		this.started.set(false);
		log.debug("submit order listener stopped!");
	}

	private void clearDoneCancelledFuture() {
		Iterator<Future<?>> itr = this.futures.iterator();
		while (itr.hasNext()) {
			Future<?> f = itr.next();
			if (f.isDone() || f.isCancelled()) {
				itr.remove();
			}
		}
	}
}
