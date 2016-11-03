package com.woyao.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snowm.global.scheduler.GlobalIdentifiedTask;
import com.woyao.utils.TimeLogger;

public class SendDailyOrderReportTask extends GlobalIdentifiedTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void call() {
		logger.debug("Starting to send daily order report...");
		TimeLogger tl = null;
		if (logger.isDebugEnabled()) {
			tl = TimeLogger.newLogger().start();
		}
		//TODO
		int i = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Sent {} daily order reports to customer! Spent time:{} ms.", i, tl.end().spent());
		}
	}
}
