package com.snowm.global.scheduler.trigger;

import java.util.TimeZone;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

public class CronTriggerEntity implements TriggerEntity {

    private static final long serialVersionUID = 8468001287426669146L;

    private String expression;

    private TimeZone timeZone;

    @Override
    public Trigger getTrigger() {
        if (expression == null) {
            return null;
        }
        if (timeZone == null) {
            return new CronTrigger(expression);
        }
        return new CronTrigger(expression, timeZone);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
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
