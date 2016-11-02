package com.snowm.global.lock;

import java.util.concurrent.TimeUnit;

public interface LockService {

    boolean doLock(String id, String owner, int timeout, TimeUnit timeUnit);

    void doUnlock(String id, String owner);

    boolean doTouchLock(String id, String owner, int timeout, TimeUnit timeUnit);
    
    boolean isLocked(String id);
}
