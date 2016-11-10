package com.woyao.scheduler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.snowm.global.scheduler.GlobalIdentifiedTask;
import com.woyao.cache.RicherReportCache;

@Component("refreshCacheTask")
public class RefreshCacheTask extends GlobalIdentifiedTask {

	@Resource(name = "richerReportCache")
	private RicherReportCache richerReportCache;

	public void call() {
		this.richerReportCache.refreshDaily();
	}

}
