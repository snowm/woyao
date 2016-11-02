package com.snowm.global.scheduler.trigger;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;

public class PeriodicTriggerEntity implements TriggerEntity {

    private static final long serialVersionUID = 4677799914319954246L;

    private long period;

    private TimeUnit timeUnit;

    private long initialDelay = 0;

    private boolean fixedRate = false;

    public PeriodicTriggerEntity() {
    }

    public PeriodicTriggerEntity(long period) {
        this.period = period;
    }

    public PeriodicTriggerEntity(long period, TimeUnit timeUnit) {
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public Trigger getTrigger() {
        PeriodicTrigger trigger = new PeriodicTrigger(period, timeUnit);
        trigger.setInitialDelay(initialDelay);
        trigger.setFixedRate(fixedRate);
        return trigger;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public boolean isFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(boolean fixedRate) {
        this.fixedRate = fixedRate;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
