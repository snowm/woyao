package com.woyao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.woyao.scheduler.GetWxGlobalAccessTokenTask;
import com.woyao.scheduler.RetrySubmitOrderTask;
import com.woyao.scheduler.SendDailyOrderReportTask;

@Configuration
@Order(3)
public class SchedulerConfig {

	@Bean(name = "getWxGlobalAccessTokenTask")
	public GetWxGlobalAccessTokenTask getWxGlobalAccessTokenTask() {
		GetWxGlobalAccessTokenTask task = new GetWxGlobalAccessTokenTask();
		task.setId("getWxGlobalAccessTokenTask");
		return task;
	}

	@Bean(name = "retrySubmitOrderTask")
	public RetrySubmitOrderTask retrySubmitOrderTask() {
		RetrySubmitOrderTask task = new RetrySubmitOrderTask();
		task.setId("retrySubmitOrderTask");
		return task;
	}

	@Bean(name = "sendDailyOrderReportTask")
	public SendDailyOrderReportTask sendDailyOrderReportTask() {
		SendDailyOrderReportTask task = new SendDailyOrderReportTask();
		task.setId("sendDailyOrderReportTask");
		return task;
	}
}
