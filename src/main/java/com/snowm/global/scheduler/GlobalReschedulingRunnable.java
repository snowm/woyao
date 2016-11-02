package com.snowm.global.scheduler;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.DelegatingErrorHandlingRunnable;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.util.ErrorHandler;

import com.snowm.global.lock.GlobalLock;
import com.snowm.global.lock.GlobalLockFactory;
import com.snowm.global.lock.OperatorType;

/**
 * Internal adapter that reschedules an underlying {@link Runnable} according to
 * the next execution time suggested by a given {@link Trigger}.
 *
 * <p>
 * Necessary because a native {@link ScheduledExecutorService} supports
 * delay-driven execution only. The flexibility of the {@link Trigger} interface
 * will be translated onto a delay for the next execution time (repeatedly).
 *
 * @author Jade Yang
 */
public final class GlobalReschedulingRunnable extends DelegatingErrorHandlingRunnable implements ScheduledFuture<Object> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final GlobalLock globalLock;

    private final String owner;

    private final ScheduledExecutorService executor;

    private SimpleTriggerContext triggerContext = new SimpleTriggerContext();

    private Trigger trigger;

    private ScheduledFuture<?> currentFuture;

    private Date scheduledExecutionTime;

    private final Object triggerContextMonitor = new Object();

    private TriggerContextService triggerContextService;

    private final String id;

    private Runnable task;

    /**
     * If current instance get the lock in the last turn
     */
    private boolean lastTurnLocked = true;

    private GlobalReschedulingRunnable(Runnable delegate, Trigger trigger, ScheduledExecutorService executor,
            ErrorHandler errorHandler, OperatorType operatorType, String owner, int timeout, TimeUnit timeUnit,
            TriggerContextService triggerContextService) {
        super(delegate, errorHandler);
        this.task = delegate;
        this.trigger = trigger;
        this.executor = executor;
        this.id = GlobalTaskUtils.getId(delegate);
        this.owner = owner;
        this.globalLock = GlobalLockFactory.createLock(operatorType, this.id, owner, timeout, timeUnit);
        this.triggerContextService = triggerContextService;
    }

    public static GlobalReschedulingRunnable createInstance(Runnable delegate, Trigger trigger, 
            ScheduledExecutorService executor, ErrorHandler errorHandler, OperatorType operatorType, 
            String owner, int timeout, TimeUnit timeUnit, TriggerContextService triggerContextService) {
        return new GlobalReschedulingRunnable(delegate, trigger, executor, errorHandler, operatorType,
                owner, timeout, timeUnit, triggerContextService);
    }

    public ScheduledFuture<?> schedule() {
        synchronized (this.triggerContextMonitor) {
            return this.internalSchedule(false);
        }
    }

    private ScheduledFuture<?> internalSchedule(boolean afterRan) {
        if (GlobalTaskUtils.isGlobalTrigger(this.task)) {
            this.trigger = GlobalTaskUtils.getTrigger(this.task);
            if (this.trigger == null) {
                return null;
            }
        }
        if (!afterRan && this.globalLock.isLocked()) {
            // If can not get the lock, suppose the task is completed now.
            Date now = new Date(GlobalTaskUtils.currentTimeMillis());
            this.triggerContext.update(now, now, now);
            this.scheduledExecutionTime = this.trigger.nextExecutionTime(this.triggerContext);
            this.lastTurnLocked = false;
        } else {
            Date localNextExecutionTime = this.trigger.nextExecutionTime(this.triggerContext);
            this.scheduledExecutionTime = localNextExecutionTime;

            SimpleTriggerContextEntity triggerContextEntity = this.triggerContextService.get(this.id);
            Date globalNextExecutionTime = null;
            if (triggerContextEntity != null) {
                this.triggerContext = new SimpleTriggerContext();
                this.syncTriggerContext(this.triggerContext, triggerContextEntity);
                try {
                    globalNextExecutionTime = this.trigger.nextExecutionTime(this.triggerContext);
                } catch (Exception ex) {
                    globalNextExecutionTime = new Date(GlobalTaskUtils.currentTimeMillis());
                }
                if (globalNextExecutionTime != null) {
                    this.scheduledExecutionTime = globalNextExecutionTime;
                }
            }
            this.lastTurnLocked = true;
            logger.debug(String.format(
                    "Owner: %1$s, task:%2$s, lastScheduledExecutionTime:%3$tF %<tT, "
                            + "lastActualExecutionTime:%4$tF %<tT, lastCompletionTime:%5$tF %<tT",
                    this.owner, this.id, this.triggerContext.lastScheduledExecutionTime(),
                    this.triggerContext.lastActualExecutionTime(), this.triggerContext.lastCompletionTime()));
            if (this.scheduledExecutionTime == null) {
                return null;
            }
            this.triggerContextService.schedule(this.id, this.scheduledExecutionTime);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Owner: %1$s, task:%2$s, nextExecutionTime:%3$tF %<tT", this.owner, this.id,
                    this.scheduledExecutionTime));
        }
        if (this.scheduledExecutionTime == null) {
            return null;
        }
        long initialDelay = this.scheduledExecutionTime.getTime() - GlobalTaskUtils.currentTimeMillis();
        if (!afterRan && this.currentFuture != null) {
            try {
                this.currentFuture.cancel(true);
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }
        this.currentFuture = this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * The task should not run in current turn, then just synchronize the next
     * execution time with Global, and set {@link #lastTurnNonLock} to true.
     * 
     * @param nextExcecutionTime
     * @return future
     */
    private ScheduledFuture<?> scheduleShouldNotRun(Date nextExcecutionTime) {
        this.scheduledExecutionTime = nextExcecutionTime;
        this.lastTurnLocked = true;

        if (this.scheduledExecutionTime == null) {
            return null;
        }
        logger.debug(String.format("Owner: %1$s, should not run, task:%2$s, nextExecutionTime:%3$tF %<tT", this.owner, this.id,
                this.scheduledExecutionTime));
        long initialDelay = this.scheduledExecutionTime.getTime() - GlobalTaskUtils.currentTimeMillis();
        this.currentFuture = this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * No lock got in current turn, then suppose the task is completed now, and
     * then update the next execution time in local, but do not update global
     * next execution time
     * 
     * @return
     */
    private ScheduledFuture<?> scheduleNonLock() {
        if (GlobalTaskUtils.isGlobalTrigger(this.task)) {
            this.trigger = GlobalTaskUtils.getTrigger(this.task);
            if (this.trigger == null) {
                return null;
            }
        }
        this.scheduledExecutionTime = this.trigger.nextExecutionTime(this.triggerContext);
        this.lastTurnLocked = false;

        if (this.scheduledExecutionTime == null) {
            return null;
        }
        logger.debug(String.format("Owner: %1$s, non lock, task:%2$s, nextExecutionTime:%3$tF %<tT", this.owner, this.id,
                this.scheduledExecutionTime));
        long initialDelay = this.scheduledExecutionTime.getTime() - GlobalTaskUtils.currentTimeMillis();
        this.currentFuture = this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * No lock got in last turn, then suppose the task is completed now, and
     * then update the next execution time in local, but do not update the
     * global next execution time
     * 
     * @return
     */
    private ScheduledFuture<?> scheduleLastTurnNonLock(Date nextExcecutionTime) {
        this.scheduledExecutionTime = nextExcecutionTime;
        this.lastTurnLocked = true;

        if (this.scheduledExecutionTime == null) {
            return null;
        }
        logger.debug(String.format("Owner: %1$s, last turn non lock, task:%2$s, nextExecutionTime:%3$tF %<tT", this.owner,
                this.id, this.scheduledExecutionTime));
        long initialDelay = this.scheduledExecutionTime.getTime() - GlobalTaskUtils.currentTimeMillis();
        this.currentFuture = this.executor.schedule(this, initialDelay, TimeUnit.MILLISECONDS);
        return this;
    }

    private boolean shouldRun() {
        try {
            // if last turn non-locked, then reschedule the task as the
            // nextExecutionTime in globalTriggerContext
            if (!this.lastTurnLocked) {
                if (!this.currentFuture.isCancelled()) {
                    SimpleTriggerContextEntity triggerContextEntity = triggerContextService.get(this.id);
                    this.scheduleLastTurnNonLock(triggerContextEntity.getNextExecutionTime());
                }
                return false;
            }
            SimpleTriggerContextEntity triggerContextEntity = triggerContextService.get(this.id);
            // maybe several instances can access in, but not at the same
            // time, check if it's should be executed
            Date now = new Date(GlobalTaskUtils.currentTimeMillis());
            if (!this.triggerContextService.shouldExecute(triggerContextEntity, now)) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format(
                            "Owner: %1$s, nextExecutionDate does not match, task:%2$s should not be executed!",
                            this.owner, this.id));
                }
                if (!this.currentFuture.isCancelled()) {
                    this.scheduleShouldNotRun(triggerContextEntity.getNextExecutionTime());
                }
                return false;
            }
            Date actualExecutionTime = new Date(GlobalTaskUtils.currentTimeMillis());
            triggerContextService.beginExecuting(this.id, actualExecutionTime);
            return true;
        } catch (RejectedExecutionException ex) {
            logger.error("Check state error before run the task!", ex);
            return false;
        } catch (Exception ex) {
            logger.warn("Check state error before run the task!", ex);
            if (!this.currentFuture.isCancelled()) {
                Date now = new Date(GlobalTaskUtils.currentTimeMillis());
                this.triggerContext.update(now, now, now);
                this.scheduleNonLock();
            }
            return false;
        }
    }

    private void postRun() {
        Date completionTime = new Date(GlobalTaskUtils.currentTimeMillis());
        this.triggerContext.update(this.triggerContext.lastScheduledExecutionTime(),
                this.triggerContext.lastActualExecutionTime(), completionTime);
        SimpleTriggerContextEntity triggerContextEntity = triggerContextService.completeExecuting(this.id,
                completionTime);
        this.syncTriggerContext(this.triggerContext, triggerContextEntity);
        if (!this.currentFuture.isCancelled()) {
            this.internalSchedule(true);
        }
    }

    @Override
    public void run() {
        synchronized (this.triggerContextMonitor) {
            try {
                boolean locked = this.globalLock.lock();
                if (locked) {
                    if (this.shouldRun()) {
                        super.run();
                        this.postRun();
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("Owner: %1$s, can not get lock, task:%2$s should not be executed!",
                                this.owner, this.id));
                    }
                    if (!this.currentFuture.isCancelled()) {
                        Date now = new Date(GlobalTaskUtils.currentTimeMillis());
                        this.triggerContext.update(now, now, now);
                        this.scheduleNonLock();
                    }
                    return;
                }
            } catch (RejectedExecutionException ex) {
                logger.error("Run the task error!", ex);
            } catch (Exception ex) {
                logger.error("Run the task error!", ex);
                if (!this.currentFuture.isCancelled()) {
                    Date now = new Date(GlobalTaskUtils.currentTimeMillis());
                    this.triggerContext.update(now, now, now);
                    this.scheduleNonLock();
                }
            } finally {
                try {
                    this.globalLock.unlock();
                } catch (Exception ex) {
                    logger.error("unlock error!", ex);
                }
            }
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        synchronized (this.triggerContextMonitor) {
            try {
                this.globalLock.unlock();
            } catch (Exception ex) {
                logger.error("unlock error!", ex);
            }
            return this.currentFuture.cancel(mayInterruptIfRunning);
        }
    }

    @Override
    public boolean isCancelled() {
        synchronized (this.triggerContextMonitor) {
            return this.currentFuture.isCancelled();
        }
    }

    @Override
    public boolean isDone() {
        synchronized (this.triggerContextMonitor) {
            return this.currentFuture.isDone();
        }
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = this.currentFuture;
        }
        return curr.get();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = this.currentFuture;
        }
        return curr.get(timeout, unit);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        ScheduledFuture<?> curr;
        synchronized (this.triggerContextMonitor) {
            curr = this.currentFuture;
        }
        return curr.getDelay(unit);
    }

    @Override
    public int compareTo(Delayed other) {
        if (this == other) {
            return 0;
        }
        long diff = getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS);
        return (diff == 0) ? 0 : ((diff < 0) ? -1 : 1);
    }

    public boolean isLastTurnLocked() {
        return lastTurnLocked;
    }

    private void syncTriggerContext(SimpleTriggerContext triggerContext, SimpleTriggerContextEntity triggerContextEntity) {
        triggerContext.update(triggerContextEntity.getLastScheduledExecutionTime(),
                triggerContextEntity.getLastActualExecutionTime(), triggerContextEntity.getLastCompletionTime());
    }

}
