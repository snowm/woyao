package com.woyao.customer.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyao.GlobalConfig;
import com.woyao.wx.WxPayEndpoint;
import com.woyao.wx.dto.UnifiedOrderRequestDTO;
import com.woyao.wx.dto.UnifiedOrderResponse;
import com.woyao.wx.service.IWxPayService;

@Controller
@RequestMapping(value = "/m/wxPay")
public class WxPayController {
	
	@Resource(name="wxPayEndpoint")
	private WxPayEndpoint wxPayEndpoint;
	
	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;
	
	@Resource(name = "wxAdminService")
	private IWxPayService wxAdminService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public UnifiedOrderResponse order(String productId,Long quantity) {
		UnifiedOrderRequestDTO dto=wxAdminService.getUnifiedDTO(productId,quantity);
		//globalConfig.getAppId(), globalConfig.getMrchId()
		UnifiedOrderResponse resp = this.wxPayEndpoint.unifiedOrder(dto,productId,quantity);
		return resp;
	}
}
