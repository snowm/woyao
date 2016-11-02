package com.snowm.global.lock;

public interface GlobalLock {

    /**
     * Try to get the lock, it's non-block method.
     * 
     * @return true, if got the lock, otherwise return false
     */
    boolean lock();

    /**
     * Try to release the lock, it always returns true, unless the global state
     * container is blocked
     * 
     * @return true
     */
    void unlock();

    /**
     * If this lock is hold by any instance
     * 
     * @return If this lock is hold by any instance
     */
    boolean isLocked();
}
