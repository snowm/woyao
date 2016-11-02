package com.snowm.global.lock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class GlobalLockFactory implements InitializingBean, DisposableBean {

	private static GlobalLockFactory instance;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private LockService sqlServerService;
	
	private LockService mySqlService;

	private ScheduledExecutorService monitorEs;

	private int monitorInterval = 30000;

	public GlobalLockFactory() {
		super();
	}

	public static GlobalLock createLock(OperatorType operatorType, String id, String owner, int timeout, TimeUnit timeUnit) {
		switch (operatorType) {
		case SQLSERVER:
			GlobalLockImpl lockSqlServer = new GlobalLockImpl(id, owner, timeout, timeUnit, instance.monitorEs, instance.monitorInterval);
			lockSqlServer.setLockService(instance.sqlServerService);
			return lockSqlServer;
		case MYSQL:
			GlobalLockImpl lockMySql = new GlobalLockImpl(id, owner, timeout, timeUnit, instance.monitorEs, instance.monitorInterval);
			lockMySql.setLockService(instance.mySqlService);
			return lockMySql;
		default:
			throw new UnsupportedOperationException("Unsupported operator type:" + operatorType);
		}
	}

	public void setSqlServerService(LockService sqlServerService) {
		this.sqlServerService = sqlServerService;
	}

	public void setMySqlService(LockService mySqlService) {
		this.mySqlService = mySqlService;
	}

	public void setMonitorInterval(int monitorInterval) {
		this.monitorInterval = monitorInterval;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.monitorEs = Executors.newScheduledThreadPool(2);
		instance = this;
	}

	@Override
	public void destroy() throws Exception {
		this.monitorEs.shutdown();
		try {
			this.monitorEs.awaitTermination(1, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			if (logger.isWarnEnabled()) {
				logger.warn("Awaiting termination of global task monitor es is interrupted!", e);
			}
		}
	}

}
