package com.woyao.scheduler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.woyao.GlobalConfig;
import com.woyao.domain.wx.GlobalAccessToken;
import com.woyao.domain.wx.JsapiTicket;
import com.woyao.service.GlobalAccessTokenService;
import com.woyao.service.JsapiTicketService;
import com.woyao.wx.WxEndpoint;
import com.woyao.wx.dto.GetGlobalAccessTokenResponse;
import com.woyao.wx.dto.GetJsapiTicketResponse;

@Component
public class GetGlobalAccessTokenJob {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "globalAccessTokenService")
	private GlobalAccessTokenService globalTokenService;

	@Resource(name = "jsapiTicketService")
	private JsapiTicketService jsapiTicketservice;

	@Resource(name = "wxEndpoint")
	private WxEndpoint wxEndpoint;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@Value("${scheduler.getGlobalToken.enabled}")
	private boolean enabled = true;

	/**
	 * 延迟10s，固定等待1m
	 */
	@Scheduled(fixedDelay = 60000, initialDelay = 10000)
	public void executeInternal() {
		if (!this.enabled) {
			logger.debug("GetGlobalAccessTokenJob is disabled...");
			return;
		}
		this.getGlobalAccessToken();
		this.getJsapiTicket();
	}

	private void getGlobalAccessToken() {
		logger.debug("Starting to get global access token...");
		long start = System.currentTimeMillis();
		try {
			GlobalAccessToken token = this.globalTokenService.getToken();
			if (token != null && token.isEffective() && !token.isExpired()) {
				logger.debug("现有token有效，没必要重新获取GlobalAccessToken!");
				return;
			}
			if (token == null) {
				token = new GlobalAccessToken();
			}
			GetGlobalAccessTokenResponse resp = wxEndpoint.getGlobalAccessToken(globalConfig.getAppId(), globalConfig.getAppSecret(),
					"client_credential");
			token.setAccessToken(resp.getAccessToken());
			token.setExpiresIn(resp.getExpiresIn());
			token.setEffective(true);
			this.globalTokenService.saveOrUpdate(token);
		} catch (Exception ex) {
			logger.error("Get global access token error!", ex);
		} finally {
			if (logger.isDebugEnabled()) {
				long spent = System.currentTimeMillis() - start;
				logger.debug("Global access token got! Spent time:{} ms.", spent);
			}
		}
	}

	private void getJsapiTicket() {
		logger.debug("Starting to get jsapi ticket...");
		long start = System.currentTimeMillis();
		try {
			JsapiTicket ticket = this.jsapiTicketservice.getTicket();
			if (ticket != null && ticket.isEffective() && !ticket.isExpired()) {
				logger.debug("现有ticket有效，没必要重新获取JsapiTicket!");
				return;
			}
			if (ticket == null) {
				ticket = new JsapiTicket();
			}
			GlobalAccessToken globalAccessToken = this.globalTokenService.getToken();
			GetJsapiTicketResponse resp = wxEndpoint.getJsapiTicket(globalAccessToken.getAccessToken());
			ticket.setTicket(resp.getTicket());
			ticket.setExpiresIn(resp.getExpiresIn());
			ticket.setEffective(true);
			this.jsapiTicketservice.saveOrUpdate(ticket);
		} catch (Exception ex) {
			logger.error("Get jsapi ticket error!", ex);
		} finally {
			if (logger.isDebugEnabled()) {
				long spent = System.currentTimeMillis() - start;
				logger.debug("Jsapi ticket got! Spent time:{} ms.", spent);
			}
		}
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}