package com.woyao.wx.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldFilter;

import com.woyao.GlobalConfig;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.dao.CommonDao;
import com.woyao.wx.WxPayEndpoint;
import com.woyao.wx.WxUtils;
import com.woyao.wx.dto.UnifiedOrderRequest;
import com.woyao.wx.dto.UnifiedOrderResponse;
import com.woyao.wx.service.IWxPayService;
import com.woyao.wx.validate.PaySignValidateFieldCallback;
import com.woyao.wx.validate.SignValidateFieldFilter;

@Service("wxPayService")
public class WxPayServiceImpl implements IWxPayService {

	private static final String TRADE_TYPE_JSAPI = "JSAPI";

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@Resource(name = "wxPayEndpoint")
	private WxPayEndpoint wxPayEndpoint;
	
	@Override
	public UnifiedOrderResponse unifiedOrder(OrderDTO order) {
		UnifiedOrderRequest request = this.builderRequest(order);
		UnifiedOrderResponse response = this.wxPayEndpoint.unifiedOrder(request);
		return response;
	}

	private UnifiedOrderRequest builderRequest(OrderDTO order) {
		UnifiedOrderRequest rs = new UnifiedOrderRequest();
		String appId = globalConfig.getAppId();
		String mchId = globalConfig.getMchId();
		String nonceStr = WxUtils.generateNonce(32);
		String body = "我要-酒水";
		String outTradeNo = order.getOrderNo();
		System.out.println(outTradeNo);
		int totalFee = order.getTotalFee();
		Date startDate = new Date();
		String spbillCreateIp = order.getSpbillCreateIp();
		String timeStart = WxUtils.formatDate(startDate);
		Date expireDate = new Date(startDate.getTime() + 10 * 60 * 1000);
		String timeExpire = WxUtils.formatDate(expireDate);
		String notifyUrl = globalConfig.getPayNotifyUrl();
		String tradeType = TRADE_TYPE_JSAPI;
		String openId = order.getConsumer().getOpenId();
		
		rs.setAppid(appId);
		rs.setMchId(mchId);
		rs.setNonceStr(nonceStr);
		rs.setBody(body);
		rs.setOutTradeNo(outTradeNo);
		rs.setTotalFee(totalFee);
		rs.setSpbillCreateIp(spbillCreateIp);
		rs.setTimeStart(timeStart);
		rs.setTimeExpire(timeExpire);
		rs.setNotifyUrl(notifyUrl);
		rs.setTradeType(tradeType);
		rs.setOpenId(openId);

		PaySignValidateFieldCallback fc = new PaySignValidateFieldCallback(rs);
		ReflectionUtils.doWithFields(rs.getClass(), fc, ff);
		String sign = WxUtils.generatePaySign(fc.getNvs(), globalConfig.getPayApiKey());
		
		rs.setSign(sign);
		return rs;
	}
	private FieldFilter ff = new SignValidateFieldFilter(new String[] { "sign" });
}
