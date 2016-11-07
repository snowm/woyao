package com.woyao.scheduler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snowm.global.scheduler.GlobalIdentifiedTask;
import com.taobao.api.ApiException;
import com.woyao.admin.service.IOrderItemAdminService;
import com.woyao.admin.shop.controller.ShopRoot;
import com.woyao.admin.shop.dto.PhoneSMS;
import com.woyao.admin.shop.dto.SMSParamsDTO;
import com.woyao.admin.shop.dto.TaoBaoDTO;
import com.woyao.domain.Shop;
import com.woyao.utils.JsonUtils;
import com.woyao.utils.TimeLogger;

public class SendDailyOrderReportTask extends GlobalIdentifiedTask {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "orderItemService")
	private IOrderItemAdminService service;
	
	@Autowired
	private PhoneSMS phoneSMS;

	public void call() {
		logger.debug("Starting to send daily order report...");
		TimeLogger tl = null;
		if (logger.isDebugEnabled()) {
			tl = TimeLogger.newLogger().start();
		}
		//TODO
		List<Shop> shops=this.service.getShop();
		for (Shop shop : shops) {		
			if (shop != null) {
				SMSParamsDTO dto = service.queryReport(shop.getId());
				try {
					TaoBaoDTO taobao = phoneSMS.getTaoBaoDTO(dto);
					phoneSMS.sendSMS(JsonUtils.toString(taobao), dto.getPhone());
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		int i = 0;
		if (logger.isDebugEnabled()) {
			logger.debug("Sent {} daily order reports to customer! Spent time:{} ms.", i, tl.end().spent());
		}
	}
}
