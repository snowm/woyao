package com.snowm.global.scheduler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class DefaultTriggerContextService implements TriggerContextService {

    private static final String SQL_GET = "SELECT id, last_scheduled_execution_dt, last_actual_execution_dt,"
            + " last_completion_dt, next_execution_dt FROM global_task_trigger_context WHERE id = :id";

    private static final String SQL_SCHEDULE = "UPDATE global_task_trigger_context"
            + " SET next_execution_dt = :nextExecutionDt WHERE id = :id";

    private static final String SQL_COMPLETE_EXE = "UPDATE global_task_trigger_context"
            + " SET last_completion_dt = :lastCompletionDt WHERE id = :id";

    private static final String SQL_BEGIN_EXE = "UPDATE global_task_trigger_context"
            + " SET last_scheduled_execution_dt = :lastScheduledExecutionDt,"
            + " last_actual_execution_dt = :lastActualExecutionDt WHERE id = :id";

	private static final String SQL_INIT = "INSERT INTO global_task_trigger_context (id)"
			+ " SELECT * FROM (SELECT :id) AS tmp"
			+ " WHERE NOT EXISTS (SELECT 1 FROM global_task_trigger_context WHERE id = :id ) LIMIT 1";

    private static final String SQL_RESET = "UPDATE global_task_trigger_context"
            + " SET last_scheduled_execution_dt = null, last_actual_execution_dt = null, last_completion_dt = null,"
            + " next_execution_dt = null WHERE id = :id";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private NamedParameterJdbcTemplate jdbcTemplate;

    private RowMapper<SimpleTriggerContextEntity> rowMapper = new TriggetContextRowMapper();

    @Override
    public void init(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        try {
            this.jdbcTemplate.update(SQL_INIT, paramMap);
        } catch (DuplicateKeyException ex) {
            // catch violate unique constraint exception
            if (logger.isWarnEnabled()) {
                logger.warn("Init trigger context conflict, but still succeeded!");
            }
        }
    }

    @Override
    public boolean shouldExecute(String id, Date now) {
        SimpleTriggerContextEntity triggerContextEntity = get(id);
        return this.shouldExecute(triggerContextEntity, now);
    }

    @Override
    public boolean shouldExecute(SimpleTriggerContextEntity triggerContextEntity, Date now) {
        if (triggerContextEntity == null) {
            return false;
        }
        Date nextExecutionTime = triggerContextEntity.getNextExecutionTime();
        if (nextExecutionTime == null) {
            return false;
        }
        return !nextExecutionTime.after(now);
    }

    @Override
    public void beginExecuting(String id, Date actualExecutionTime) {
        init(id);
//        SimpleTriggerContextEntity triggerContextEntity = this.get(id);
//        Date nextExecutionTime = triggerContextEntity.getNextExecutionTime();
//        Date lastScheduledExecutionTime = (nextExecutionTime == null) ? actualExecutionTime : nextExecutionTime;
        Date lastScheduledExecutionTime = actualExecutionTime;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("lastScheduledExecutionDt", new Timestamp(lastScheduledExecutionTime.getTime()));
        paramMap.put("lastActualExecutionDt", new Timestamp(actualExecutionTime.getTime()));
        this.jdbcTemplate.update(SQL_BEGIN_EXE, paramMap);
    }

    @Override
    public SimpleTriggerContextEntity completeExecuting(String id, Date completionTime) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("lastCompletionDt", new Timestamp(completionTime.getTime()));
        this.jdbcTemplate.update(SQL_COMPLETE_EXE, paramMap);

        SimpleTriggerContextEntity triggerContextEntity = get(id);
        return triggerContextEntity;
    }

    @Override
    public void schedule(String id, Date nextExecutionTime) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("nextExecutionDt", new Timestamp(nextExecutionTime.getTime()));
        this.jdbcTemplate.update(SQL_SCHEDULE, paramMap);
    }

    @Override
    public void reset(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        this.jdbcTemplate.update(SQL_RESET, paramMap);
    }

    @Override
    public SimpleTriggerContextEntity get(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        try {
            SimpleTriggerContextEntity context = this.jdbcTemplate.queryForObject(SQL_GET, paramMap, this.rowMapper);
            return context;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private class TriggetContextRowMapper implements RowMapper<SimpleTriggerContextEntity> {

        @Override
        public SimpleTriggerContextEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            SimpleTriggerContextEntity context = new SimpleTriggerContextEntity(rs.getString(1));
            Timestamp tmpDate = rs.getTimestamp(2);
            if (tmpDate != null) {
                context.setLastScheduledExecutionTime(new Date(tmpDate.getTime()));
            }
            tmpDate = rs.getTimestamp(3);
            if (tmpDate != null) {
                context.setLastActualExecutionTime(new Date(tmpDate.getTime()));
            }
            tmpDate = rs.getTimestamp(4);
            if (tmpDate != null) {
                context.setLastCompletionTime(new Date(tmpDate.getTime()));
            }
            tmpDate = rs.getTimestamp(5);
            if (tmpDate != null) {
                context.setNextExecutionTime(new Date(tmpDate.getTime()));
            }
            return context;
        }

    }

}
