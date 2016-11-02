package com.snowm.global.scheduler;

import org.springframework.scheduling.Trigger;

public abstract class GlobalTriggerTask extends GlobalIdentifiedTask {

    public abstract Trigger getTrigger();
}
