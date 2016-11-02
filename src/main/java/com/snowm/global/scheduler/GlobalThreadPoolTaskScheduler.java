package com.snowm.global.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.util.ErrorHandler;

import com.snowm.global.lock.OperatorType;

public class GlobalThreadPoolTaskScheduler extends ThreadPoolTaskScheduler {

    private static final long serialVersionUID = -626488264850690079L;

    private volatile ErrorHandler errorHandler;

    private OperatorType operatorType;

    private String owner;

    private int timeout;

    private TimeUnit timeUnit;

    private TriggerContextService triggerContextService;

    private final Map<String, Runnable> taskMap = new HashMap<>();

    private final Map<String, ScheduledFuture<?>> scheduledFutureMap = new HashMap<>();

    // TaskScheduler implementation

    public synchronized ScheduledFuture<?> reschedule(String taskId, Trigger trigger) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Owner: %1$s, Rescheduling task: %2$s", this.owner, taskId));
        }
        this.stop(taskId, false);
        if (trigger == null) {
            this.triggerContextService.reset(taskId);
            return null;
        }
        SimpleTriggerContextEntity triggerContextEntity = this.triggerContextService.get(taskId);
        SimpleTriggerContext triggerContext = new SimpleTriggerContext(triggerContextEntity.getLastScheduledExecutionTime(),
                triggerContextEntity.getLastActualExecutionTime(), triggerContextEntity.getLastCompletionTime());
        Date nextExecutionTime = trigger.nextExecutionTime(triggerContext);
        this.triggerContextService.schedule(taskId, nextExecutionTime);
        return this.start(taskId, trigger);
    }

    public synchronized void stop(String taskId, boolean reset) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Owner: %1$s, Stop task: %2$s", this.owner, taskId));
        }
        ScheduledFuture<?> scheduledFuture = this.scheduledFutureMap.get(taskId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.scheduledFutureMap.remove(taskId);
        }
        if (reset) {
            this.triggerContextService.reset(taskId);
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Owner: %1$s, Task: %2$s stopped", this.owner, taskId));
        }
    }

    public synchronized ScheduledFuture<?> start(String taskId, Trigger trigger) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Owner: %1$s, Start task: %2$s", this.owner, taskId));
        }
        Runnable task = this.taskMap.get(taskId);
        ScheduledFuture<?> future = this.schedule(task, trigger);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Owner: %1$s, Task: %2$s started", this.owner, taskId));
        }
        return future;
    }

    @Override
    public synchronized ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        ScheduledExecutorService executor = getScheduledExecutor();
        try {
            ErrorHandler eh = (this.errorHandler != null) ? this.errorHandler : TaskUtils.getDefaultErrorHandler(true);
            String taskId = GlobalTaskUtils.getId(task);
            this.triggerContextService.init(taskId);
            ScheduledFuture<?> scheduledFuture = GlobalReschedulingRunnable
                    .createInstance(task, trigger, executor, eh, operatorType, owner, timeout, timeUnit, triggerContextService)
                    .schedule();
            this.storeScheduleTaskInfo(taskId, task, scheduledFuture);
            return scheduledFuture;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public synchronized ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        ScheduledExecutorService executor = getScheduledExecutor();
        long initialDelay = startTime.getTime() - GlobalTaskUtils.currentTimeMillis();
        try {
            PeriodicTrigger trigger = new PeriodicTrigger(period);
            trigger.setFixedRate(true);
            trigger.setInitialDelay(initialDelay);
            ErrorHandler eh = (this.errorHandler != null) ? this.errorHandler : TaskUtils.getDefaultErrorHandler(true);
            String taskId = GlobalTaskUtils.getId(task);
            this.triggerContextService.init(taskId);
            ScheduledFuture<?> scheduledFuture = GlobalReschedulingRunnable
                    .createInstance(task, trigger, executor, eh, operatorType, owner, timeout, timeUnit, triggerContextService)
                    .schedule();
            this.storeScheduleTaskInfo(taskId, task, scheduledFuture);
            return scheduledFuture;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public synchronized ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        ScheduledExecutorService executor = getScheduledExecutor();
        try {
            PeriodicTrigger trigger = new PeriodicTrigger(period);
            trigger.setFixedRate(true);
            ErrorHandler eh = (this.errorHandler != null) ? this.errorHandler : TaskUtils.getDefaultErrorHandler(true);
            String taskId = GlobalTaskUtils.getId(task);
            this.triggerContextService.init(taskId);
            ScheduledFuture<?> scheduledFuture = GlobalReschedulingRunnable
                    .createInstance(task, trigger, executor, eh, operatorType, owner, timeout, timeUnit, triggerContextService)
                    .schedule();
            this.storeScheduleTaskInfo(taskId, task, scheduledFuture);
            return scheduledFuture;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public synchronized ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        ScheduledExecutorService executor = getScheduledExecutor();
        long initialDelay = startTime.getTime() - GlobalTaskUtils.currentTimeMillis();
        try {
            PeriodicTrigger trigger = new PeriodicTrigger(delay);
            trigger.setInitialDelay(initialDelay);
            ErrorHandler eh = (this.errorHandler != null) ? this.errorHandler : TaskUtils.getDefaultErrorHandler(true);
            String taskId = GlobalTaskUtils.getId(task);
            this.triggerContextService.init(taskId);
            ScheduledFuture<?> scheduledFuture = GlobalReschedulingRunnable
                    .createInstance(task, trigger, executor, eh, operatorType, owner, timeout, timeUnit, triggerContextService)
                    .schedule();
            this.storeScheduleTaskInfo(taskId, task, scheduledFuture);
            return scheduledFuture;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public synchronized ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        ScheduledExecutorService executor = getScheduledExecutor();
        try {
            PeriodicTrigger trigger = new PeriodicTrigger(delay);
            ErrorHandler eh = (this.errorHandler != null) ? this.errorHandler : TaskUtils.getDefaultErrorHandler(true);
            String taskId = GlobalTaskUtils.getId(task);
            this.triggerContextService.init(taskId);
            ScheduledFuture<?> scheduledFuture = GlobalReschedulingRunnable
                    .createInstance(task, trigger, executor, eh, operatorType, owner, timeout, timeUnit, triggerContextService)
                    .schedule();
            this.storeScheduleTaskInfo(taskId, task, scheduledFuture);
            return scheduledFuture;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    private void storeScheduleTaskInfo(String taskId, Runnable task, ScheduledFuture<?> scheduledFuture) {
        this.taskMap.put(taskId, task);
        if (scheduledFuture != null) {
            this.scheduledFutureMap.put(taskId, scheduledFuture);
        }
    }

    public void destroy() {
        for (ScheduledFuture<?> future : this.scheduledFutureMap.values()) {
            if (future != null) {
                future.cancel(true);
            }
        }
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setOperatorType(OperatorType operatorType) {
        this.operatorType = operatorType;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setTriggerContextService(TriggerContextService triggerContextService) {
        this.triggerContextService = triggerContextService;
    }

}
