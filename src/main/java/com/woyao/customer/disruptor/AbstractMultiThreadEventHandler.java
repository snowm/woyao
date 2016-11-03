package com.woyao.customer.disruptor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMultiThreadEventHandler<T> extends AbstractEventHandler<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private int threads = 2;

    private ExecutorService es;

    private long taskTimeout = 10L;

    private TimeUnit taskTimeoutTimeUnit = TimeUnit.SECONDS;

    @Override
    public void onStart() {
        this.es = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void onEvent(T event, long sequence, boolean endOfBatch) throws Exception {
        Callable<?> task = this.createTask(event, sequence, endOfBatch);
        Future<?> future = this.es.submit(task);
        future.get(taskTimeout, taskTimeoutTimeUnit);
    }

    @Override
    public void onShutdown() {
        logger.debug("Shutdown EventHandler:{}!", this.getName());
        super.onShutdown();
        if (!this.es.isShutdown()) {
            this.es.shutdown();
        }
        try {
            this.es.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException ex) {
            String msg = String.format("Shutdown EventHandler: %s is interrupted!", this.getName());
            logger.error(msg, ex);
        }
        logger.debug("EventHandler:{} is down!", this.getName());
    }

    protected abstract void doTask(T event, long sequence, boolean endOfBatch);

    private Callable<?> createTask(T event, long sequence, boolean endOfBatch) {
        return new HandleEventTask(this, event, sequence, endOfBatch);
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public void setTaskTimeout(long taskTimeout) {
        this.taskTimeout = taskTimeout;
    }

    public void setTaskTimeoutTimeUnit(TimeUnit taskTimeoutTimeUnit) {
        this.taskTimeoutTimeUnit = taskTimeoutTimeUnit;
    }

    private final class HandleEventTask implements Callable<Boolean> {

        private AbstractMultiThreadEventHandler<T> handler;

        private T event;

        private long sequence;

        private boolean endOfBatch;

        private HandleEventTask(AbstractMultiThreadEventHandler<T> handler, T event, long sequence,
                boolean endOfBatch) {
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
