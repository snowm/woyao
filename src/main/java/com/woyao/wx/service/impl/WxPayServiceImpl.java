package com.woyao.wx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;

import com.woyao.GlobalConfig;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.dao.CommonDao;
import com.woyao.wx.WxPayEndpoint;
import com.woyao.wx.WxUtils;
import com.woyao.wx.dto.UnifiedOrderRequest;
import com.woyao.wx.dto.UnifiedOrderResponse;
import com.woyao.wx.service.IWxPayService;

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
		String outTradeNo = order.getId() + "";
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

		List<NameValuePair> parameters = new ArrayList<>();
		try {
			parameters.add(WxUtils.generateNVPair("appid", appId));
			parameters.add(WxUtils.generateNVPair("mch_id", mchId));
			parameters.add(WxUtils.generateNVPair("nonce_str", nonceStr));
			parameters.add(WxUtils.generateNVPair("body", body));
			parameters.add(WxUtils.generateNVPair("out_trade_no", outTradeNo));
			parameters.add(WxUtils.generateNVPair("total_fee", totalFee+""));
			parameters.add(WxUtils.generateNVPair("spbill_create_ip", spbillCreateIp));
			parameters.add(WxUtils.generateNVPair("time_start", timeStart));
			parameters.add(WxUtils.generateNVPair("time_expire", timeExpire));
			parameters.add(WxUtils.generateNVPair("notify_url", notifyUrl));
			parameters.add(WxUtils.generateNVPair("trade_type", tradeType));
			parameters.add(WxUtils.generateNVPair("openid", openId));
			parameters.add(WxUtils.generateNVPair("body", body));
			// parameters.add(WxUtils.generateNVPair("signType",
			// rs.getSignType()));
		} catch (Exception ex) {
			return null;
		}
		String sign = WxUtils.generateSign(parameters, globalConfig.getPayApiKey());
		rs.setSign(sign);
		return rs;
	}

}
