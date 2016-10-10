package com.woyao.customer.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyao.GlobalConfig;
import com.woyao.wx.WxPayEndpoint;
import com.woyao.wx.dto.UnifiedOrderResponse;

@Controller
@RequestMapping(value = "/m/wxPay")
public class WxPayController {
	
	@Resource(name="wxPayEndpoint")
	private WxPayEndpoint wxPayEndpoint;
	
	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void order() {
		UnifiedOrderResponse resp = this.wxPayEndpoint.unifiedOrder(globalConfig.getAppId(), globalConfig.getMrchId());
	}
}
