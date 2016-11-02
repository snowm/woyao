package com.snowm.global.lock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

abstract class BaseRMDBDao {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private NamedParameterJdbcTemplate jdbcTemplate;

    private RowMapper<LockEntity> rowMapper = new LockEntityRowMapper();

    protected BaseRMDBDao() {
        super();
    }

    protected void deleteLock(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        try {
            this.jdbcTemplate.update(generateDeleteLockSQL(), paramMap);
        } catch (DataAccessException ex) {
            logger.warn("Delete lock error!", ex);
        }
    }

    protected boolean addLock(String id, String owner, int timeout, TimeUnit timeUnit) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("owner", owner);
        paramMap.put("timeout", timeUnit.toSeconds(timeout));
        try {
            this.jdbcTemplate.update(generateAddLockSQL(), paramMap);
            return true;
        } catch (DuplicateKeyException ex) {
        } catch (DataAccessException ex) {
            logger.warn("Add lock error!", ex);
        }
        return false;
    }

    protected LockEntity getLock(String id, String owner) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("owner", owner);
        try {
            LockEntity lockEntity = this.jdbcTemplate.queryForObject(generateGetLockSQL(), paramMap, this.rowMapper);
            return lockEntity;
        } catch (EmptyResultDataAccessException ex) {
        } catch (DataAccessException ex) {
            logger.warn("Get lock error!", ex);
        }
        return null;
    }
    
    protected LockEntity getLock(String id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        try {
            LockEntity lockEntity = this.jdbcTemplate.queryForObject(generateGetSQL(), paramMap, this.rowMapper);
            return lockEntity;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            logger.warn("Get lock error!", ex);
            return null;
        }
    }

    protected boolean touchLock(String id, String owner, int timeout, TimeUnit timeUnit) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("owner", owner);
        paramMap.put("timeout", timeUnit.toSeconds(timeout));
        try {
            int c = this.jdbcTemplate.update(generateTouchLockSQL(), paramMap);

            return c > 0;
        } catch (DataAccessException ex) {
            logger.warn("Touch lock error!", ex);
        }
        return false;
    }

    protected void deleteOwnedLock(String id, String owner) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("owner", owner);
        try {
            this.jdbcTemplate.update(generateDeleteMyLockSQL(), paramMap);
        } catch (DataAccessException ex) {
            logger.warn("Delete owned lock error!", ex);
        }
    }

    protected abstract String generateGetSQL();
    
    protected abstract String generateGetLockSQL();

    protected abstract String generateAddLockSQL();

    protected abstract String generateTouchLockSQL();

    protected abstract String generateDeleteLockSQL();

    protected abstract String generateDeleteMyLockSQL();

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class LockEntityRowMapper implements RowMapper<LockEntity> {

        @Override
        public LockEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            LockEntity entity = new LockEntity();
            entity.setId(rs.getString(1));
            entity.setOwner(rs.getString(2));

            Timestamp tmpDate = rs.getTimestamp(3);
            if (tmpDate != null) {
                entity.setCreatedDate(new Date(tmpDate.getTime()));
            }
            tmpDate = rs.getTimestamp(4);
            if (tmpDate != null) {
                entity.setTimedue(new Date(tmpDate.getTime()));
            }
            tmpDate = rs.getTimestamp(5);
            if (tmpDate != null) {
                entity.setNow(new Date(tmpDate.getTime()));
            }
            return entity;
        }

    }
}
