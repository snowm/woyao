package com.woyao.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.snowm.global.scheduler.GlobalIdentifiedTask;
import com.snowm.utils.query.PaginationBean;
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.admin.service.IOrderAdminService;
import com.woyao.admin.shop.dto.PhoneSMS;
import com.woyao.admin.shop.dto.SMSParamsDTO;
import com.woyao.admin.shop.dto.TaoBaoDTO;
import com.woyao.utils.JsonUtils;
import com.woyao.utils.TimeLogger;

public class SendDailyOrderReportTask extends GlobalIdentifiedTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "orderAdminService")
	private IOrderAdminService service;

	@Autowired
	private PhoneSMS phoneSMS;

	private List<SMSParamsDTO> failed = new ArrayList<>();

	private int triggerHour = 9;

	private AtomicBoolean triggered = new AtomicBoolean(false);

	public void call() {
		logger.debug("Starting to send daily order report...");
		TimeLogger tl = null;
		if (logger.isDebugEnabled()) {
			tl = TimeLogger.newLogger().start();
		}
		if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < triggerHour) {
			logger.debug("Don't send report.");
			return;
		}
		if (triggered.get()) {
			logger.debug("Triggered, don't send report.");
			this.failed = this.sendSMS(this.failed);
			return;
		}
		PaginationQueryRequestDTO request = new PaginationQueryRequestDTO();
		request.setPageNumber(1L);
		request.setPageSize(20);

		PaginationBean<SMSParamsDTO> pb = null;

		int success = 0;
		int fail = 0;
		try {
			while (!CollectionUtils.isEmpty((pb = this.service.listShopDailyReports(request)).getResults())) {
				List<SMSParamsDTO> f = this.sendSMS(pb.getResults());
				this.failed.addAll(f);
				success += pb.getResults().size() - f.size();
				fail += f.size();
				request.setPageNumber(request.getPageNumber() + 1);
			}
			triggered.set(true);
		} catch (Exception ex) {
			triggered.set(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Sent {} daily order reports to customer! {} failed! Spent time:{} ms.", success, fail, tl.end().spent());
		}
	}

	private List<SMSParamsDTO> sendSMS(List<SMSParamsDTO> dtos) {
		List<SMSParamsDTO> f = new ArrayList<>();
		for (SMSParamsDTO e : dtos) {
			try {
				TaoBaoDTO taobao = phoneSMS.getTaoBaoDTO(e);
				String phoneNumber = e.getPhone();
				if (StringUtils.isBlank(phoneNumber)) {
					continue;
				}
				phoneSMS.sendSMS(JsonUtils.toString(taobao), phoneNumber);
			} catch (Exception ex) {
				f.add(e);
				logger.error("Send daily report to shop :" + e.getName(), ex);
			}
		}
		return f;
	}
}
