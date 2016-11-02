package com.snowm.global.scheduler.trigger;

import org.springframework.scheduling.Trigger;

public interface ActiveTrigger extends Trigger {

    SchedulePoint getSchedulePoint();

}
