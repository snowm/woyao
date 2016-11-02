package com.woyao.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

import com.woyao.GlobalConfig;
import com.woyao.customer.disruptor.LongEventProducer;
import com.woyao.customer.service.IOrderService;
import com.woyao.scheduler.GetWxGlobalAccessTokenTask;
import com.woyao.scheduler.RetrySubmitOrderTask;
import com.woyao.scheduler.SendDailyOrderReportTask;
import com.woyao.service.GlobalAccessTokenService;
import com.woyao.service.JsapiTicketService;
import com.woyao.wx.WxEndpoint;

@Configuration
@Order(3)
public class SchedulerConfig {

	@Bean(name = "getWxGlobalAccessTokenTask")
	public GetWxGlobalAccessTokenTask getWxGlobalAccessTokenTask(
			@Qualifier("globalAccessTokenService") GlobalAccessTokenService globalTokenService,
			@Qualifier("jsapiTicketService") JsapiTicketService jsapiTicketService, 
			@Qualifier("wxEndpoint") WxEndpoint wxEndpoint,
			@Qualifier("globalConfig") GlobalConfig globalConfig, 
			@Value("${scheduler.getGlobalToken.enabled}") boolean enabled) {
		GetWxGlobalAccessTokenTask task = new GetWxGlobalAccessTokenTask();
		task.setId("getWxGlobalAccessTokenTask");
		task.setGlobalTokenService(globalTokenService);
		task.setJsapiTicketService(jsapiTicketService);
		task.setWxEndpoint(wxEndpoint);
		task.setGlobalConfig(globalConfig);
		task.setEnabled(enabled);
		return task;
	}

	@Bean(name = "retrySubmitOrderTask")
	public RetrySubmitOrderTask retrySubmitOrderTask() {
		RetrySubmitOrderTask task = new RetrySubmitOrderTask();
		task.setId("retrySubmitOrderTask");
//		task.setOrderService(orderService);
//		task.setSubmitOrderProducer(submitOrderProducer);
//		task.setBatchSize(batchSize);
		return task;
	}

	@Bean(name = "sendDailyOrderReportTask")
	public SendDailyOrderReportTask sendDailyOrderReportTask() {
		SendDailyOrderReportTask task = new SendDailyOrderReportTask();
		task.setId("sendDailyOrderReportTask");
		return task;
	}
}
