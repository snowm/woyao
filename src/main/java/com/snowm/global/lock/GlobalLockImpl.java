package com.snowm.global.lock;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalLockImpl implements GlobalLock {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private String id;

    private int timeout;

    private TimeUnit timeUnit;

    private String owner;

    private volatile boolean locked = false;

    private ScheduledExecutorService es;

    private long monitorInterval;

    private ScheduledFuture<?> future;

    private LockService lockService;

    public GlobalLockImpl(String id, String owner, int timeout, TimeUnit timeUnit, ScheduledExecutorService es,
            long monitorInterval) {
        super();
        this.id = id;
        this.owner = owner;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.es = es;
        this.monitorInterval = monitorInterval;
    }

    @Override
    public synchronized boolean lock() {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Try to get lock with id:%1$s, owner:%2$s", this.id, this.owner));
        }

        this.locked = this.lockService.doLock(id, owner, timeout, timeUnit);
        if (this.locked) {
            this.startLockBackendMonitor();
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Get lock with id:%1$s, owner:%2$s, locked:%3$s", this.id, this.owner, this.locked));
        }
        return this.locked;
    }

    @Override
    public synchronized void unlock() {
        if (!this.locked) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Try to unlock with id:%1$s, owner:%2$s", this.id, this.owner));
        }
        this.lockService.doUnlock(id, owner);
        this.locked = false;
        this.stopLockBackendMonitor();
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Unlock with id:%1$s, owner:%2$s, locked:%3$s", this.id, this.owner, this.locked));
        }
    }

    @Override
    public synchronized boolean isLocked() {
        return this.lockService.isLocked(this.id);
    }
    
    private synchronized boolean touchLock(int timeout, TimeUnit timeUnit) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Try to touch lock with id:%1$s, owner:%2$s", this.id, this.owner));
        }
        if (this.locked) {
            this.locked = this.lockService.doTouchLock(this.id, this.owner, timeout, timeUnit);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Touched lock with id:%1$s, owner:%2$s, locked:%3$s", this.id, this.owner, this.locked));
        }
        return this.locked;
    }

    private synchronized void startLockBackendMonitor() {
        this.stopLockBackendMonitor();
        Runnable command = new BackendMonitorTask(this, this.timeout, this.timeUnit);
        this.future = this.es.scheduleAtFixedRate(command, this.monitorInterval, this.monitorInterval, TimeUnit.MILLISECONDS);
    }

    private synchronized void stopLockBackendMonitor() {
        if (this.future != null) {
            if (this.future.isCancelled() || this.future.isDone()) {
                return;
            }
            this.future.cancel(true);
            this.future = null;
        }
    }

    protected String getId() {
        return id;
    }

    protected int getTimeout() {
        return timeout;
    }

    protected TimeUnit getTimeUnit() {
        return timeUnit;
    }

    protected String getOwner() {
        return owner;
    }
    
    public void setLockService(LockService lockService) {
        this.lockService = lockService;
    }

    private class BackendMonitorTask implements Runnable {

        private int timeout;
        private TimeUnit timeUnit;
        private GlobalLockImpl lock;

        public BackendMonitorTask(GlobalLockImpl lock, int timeout, TimeUnit timeUnit) {
            super();
            this.lock = lock;
            this.timeout = timeout;
            this.timeUnit = timeUnit;
        }

        @Override
        public void run() {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Backend monitor for task: %1$s start...", this.lock.id));
            }
            if (Thread.currentThread().isInterrupted()) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("Backend monitor for task: %1$s is interrupted!", this.lock.id));
                }
                return;
            }
            boolean isLocked = false;
            try {
                if (this.lock.locked) {
                    isLocked = this.lock.touchLock(this.timeout, this.timeUnit);
                } else {
                    this.lock.unlock();
                }
            } catch (Exception ex) {
            }
            if (!isLocked) {
                if (logger.isWarnEnabled()) {
                    logger.warn(String.format("Lock with id %1$s is unlocked!", this.lock.id));
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Backend monitor for task: %1$s end!", this.lock.id));
            }
        }

    }

}
