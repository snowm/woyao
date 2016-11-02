package com.snowm.global.scheduler.trigger;

import java.io.Serializable;

import org.springframework.scheduling.Trigger;

public interface TriggerEntity extends Serializable {

    Trigger getTrigger();
}
