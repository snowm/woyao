package com.snowm.global.scheduler;

public interface TriggerContextService {

    void init(String id);

    /**
     * if trigger.nextExecutionTime is before than or equal to
     * nextScheduledExecutionTime in db, return true;
     * 
     * @param id
     * @param now
     * @return
     */
    boolean shouldExecute(String id, java.util.Date now);

    boolean shouldExecute(SimpleTriggerContextEntity triggerContextEntity, java.util.Date now);

    /**
     * update the lastScheduledExecutionTime to now or nextExecutionTime,
     * lastActualExecutionTime to now, lastCompletionTime to null
     * 
     * @param id
     * @param actualExecutionTime
     * @return
     */
    void beginExecuting(String id, java.util.Date actualExecutionTime);

    /**
     * update the lastCompletionTime to now, and update the nextExecutionTime
     * 
     * @param id
     * @param completionTime
     * @return
     */
    SimpleTriggerContextEntity completeExecuting(String id, java.util.Date completionTime);
    
    void schedule(String id, java.util.Date nextExecutionTime);

    void reset(String id);

    SimpleTriggerContextEntity get(String id);

}
