package com.snowm.global.scheduler;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SimpleTriggerContextEntity implements Serializable {

    private static final long serialVersionUID = -3216502260374755429L;

    private String id;

    private Date lastScheduledExecutionTime;

    private Date lastActualExecutionTime;

    private Date lastCompletionTime;

    private Date nextExecutionTime;
    
    public SimpleTriggerContextEntity(String id) {
        super();
        this.id = id;
    }

    public SimpleTriggerContextEntity(String id, Date lastScheduledExecutionTime, Date lastActualExecutionTime,
            Date lastCompletionTime, Date nextExecutionTime) {
        super();
        this.id = id;
        this.lastScheduledExecutionTime = lastScheduledExecutionTime;
        this.lastActualExecutionTime = lastActualExecutionTime;
        this.lastCompletionTime = lastCompletionTime;
        this.nextExecutionTime = nextExecutionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLastScheduledExecutionTime() {
        return lastScheduledExecutionTime;
    }

    public void setLastScheduledExecutionTime(Date lastScheduledExecutionTime) {
        this.lastScheduledExecutionTime = lastScheduledExecutionTime;
    }

    public Date getLastActualExecutionTime() {
        return lastActualExecutionTime;
    }

    public void setLastActualExecutionTime(Date lastActualExecutionTime) {
        this.lastActualExecutionTime = lastActualExecutionTime;
    }

    public Date getLastCompletionTime() {
        return lastCompletionTime;
    }

    public void setLastCompletionTime(Date lastCompletionTime) {
        this.lastCompletionTime = lastCompletionTime;
    }

    public Date getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(Date nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
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
