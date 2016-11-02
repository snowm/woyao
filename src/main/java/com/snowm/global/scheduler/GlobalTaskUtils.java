package com.snowm.global.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.scheduling.support.SimpleTriggerContext;

public final class GlobalTaskUtils {

    private static Logger logger = LoggerFactory.getLogger(GlobalTaskUtils.class);

    private GlobalTaskUtils() {
    }

    public static String getId(Runnable delegate) {
        ScheduledMethodRunnable r = (ScheduledMethodRunnable) delegate;
        Object target = r.getTarget();
        if (target instanceof GlobalIdentifiedTask) {
            GlobalIdentifiedTask task = (GlobalIdentifiedTask) target;
            return task.getId();
        }
        return null;
    }

    public static boolean isGlobalTrigger(Runnable delegate) {
        ScheduledMethodRunnable r = (ScheduledMethodRunnable) delegate;
        Object target = r.getTarget();
        return target instanceof GlobalTriggerTask;
    }

    public static Trigger getTrigger(Runnable delegate) {
        ScheduledMethodRunnable r = (ScheduledMethodRunnable) delegate;
        Object target = r.getTarget();
        GlobalTriggerTask task = (GlobalTriggerTask) target;
        try {
            return task.getTrigger();
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return null;
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static SimpleTriggerContext cloneTriggerContext(SimpleTriggerContext context) {
        SimpleTriggerContext cloned = new SimpleTriggerContext();
        cloned.update(context.lastScheduledExecutionTime(), context.lastActualExecutionTime(), context.lastCompletionTime());
        return cloned;
    }

}
