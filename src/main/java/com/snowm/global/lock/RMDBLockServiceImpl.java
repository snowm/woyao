package com.snowm.global.lock;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMDBLockServiceImpl implements LockService {

    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(getClass());

    private BaseRMDBDao rmdbDao;

    @Override
    public boolean doLock(String id, String owner, int timeout, TimeUnit timeUnit) {
        LockEntity lockEntity = rmdbDao.getLock(id, owner);

        if (lockEntity != null) {
            // If the entity is not found, that means this lock is timeout, and
            // is removed by others
            
            return rmdbDao.touchLock(id, owner, timeout, timeUnit);
        }

        rmdbDao.deleteLock(id);
        return rmdbDao.addLock(id, owner, timeout, timeUnit);
    }

    @Override
    public void doUnlock(String id, String owner) {
        rmdbDao.deleteOwnedLock(id, owner);
    }

    @Override
    public boolean doTouchLock(String id, String owner, int timeout, TimeUnit timeUnit) {
        return rmdbDao.touchLock(id, owner, timeout, timeUnit);
    }

    @Override
    public boolean isLocked(String id) {
        return rmdbDao.getLock(id) != null;
    }
    
    public void setRmdbDao(BaseRMDBDao rmdbDao) {
        this.rmdbDao = rmdbDao;
    }

}
