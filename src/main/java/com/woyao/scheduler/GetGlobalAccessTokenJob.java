package com.woyao.scheduler;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.woyao.GlobalConfig;
import com.woyao.domain.wx.GlobalAccessToken;
import com.woyao.service.GlobalAccessTokenService;
import com.woyao.wx.WxEndpoint;
import com.woyao.wx.dto.GetGlobalAccessTokenResponse;

@Component
public class GetGlobalAccessTokenJob {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "globalAccessTokenService")
	private GlobalAccessTokenService service;

	@Resource(name = "wxEndpoint")
	private WxEndpoint wxEndpoint;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	private boolean enabled = false;

	/**
	 * 延迟20s，固定等待2m
	 */
	@Scheduled(fixedDelay = 120000, initialDelay = 20000)
	public void executeInternal() {
		if (!this.enabled) {
			this.log.debug("GetGlobalAccessTokenJob is disabled...");
			return;
		}
		if (this.log.isDebugEnabled()) {
			this.log.debug("Starting to get global access token...");
		}
		long start = System.currentTimeMillis();
		try {
			GlobalAccessToken token = this.service.getToken();
			if (token == null || !token.isEffective() || token.isExpired() || token.getRemainExpiringTime() <= 600) {
				if (token == null) {
					token = new GlobalAccessToken();
				}
				GetGlobalAccessTokenResponse resp = wxEndpoint.getGlobalAccessToken(globalConfig.getAppId(), globalConfig.getAppSecret(),
						"client_credential");
				token.setAccessToken(resp.getAccessToken());
				token.setExpiresIn(resp.getExpiresIn());
				token.setEffective(true);
				this.service.saveOrUpdate(token);
			}
		} finally {
			if (this.log.isDebugEnabled()) {
				long spent = System.currentTimeMillis() - start;
				this.log.debug("Global access token got! Spent time:" + spent + " ms");
			}
		}
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}