package com.woyao.customer.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;

public abstract class AbstractEventHandler<T> implements EventHandler<T>, LifecycleAware {

    protected String name;

    private boolean destroyed = false;

    public AbstractEventHandler() {
    }

    public AbstractEventHandler(String name) {
        this.name = name;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onEvent(T event, long sequence, boolean endOfBatch) throws Exception {
        this.doTask(event, sequence, endOfBatch);
    }

    @Override
    public void onShutdown() {
        this.destroyed = true;
    }

    protected abstract void doTask(T event, long sequence, boolean endOfBatch);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

}
