package com.woyao.customer.dto.chat;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class LockScope implements AutoCloseable {

	private Lock lock;

	private LockScope(Lock l) {
		if (l != null) {
			this.lock = l;
			l.lock();
		}
	}
	
	public static LockScope read(ReadWriteLock readWriteLock) {
		return new LockScope(readWriteLock != null ? readWriteLock.readLock() : null);
	}
	
	public static LockScope write(ReadWriteLock readWriteLock) {
		return new LockScope(readWriteLock != null ? readWriteLock.writeLock() : null);
	}
	
	@Override
	public void close() {
		Lock l = this.lock;
		if (l != null) {
			this.lock = null;
			l.unlock();
		}
	}
}
