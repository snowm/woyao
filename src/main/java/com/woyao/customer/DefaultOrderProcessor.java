package com.woyao.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.woyao.GlobalConfig;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.dto.PrepayInfoDTO;
import com.woyao.customer.dto.chat.out.ErrorOutbound;
import com.woyao.customer.dto.chat.out.PrepayMsgDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IOrderService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.purchase.OrderPrepayInfo;
import com.woyao.domain.purchase.OrderResultInfo;
import com.woyao.domain.purchase.OrderStatus;
import com.woyao.wx.WxUtils;
import com.woyao.wx.dto.UnifiedOrderResponse;
import com.woyao.wx.service.IWxPayService;

@Component("defaultOrderProcessor")
public class DefaultOrderProcessor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "wxPayService")
	private IWxPayService wxPayService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	public PrepayInfoDTO process(OrderDTO order) {
		UnifiedOrderResponse resp = wxPayService.unifiedOrder(order);
		OrderPrepayInfo m = new OrderPrepayInfo();
		BeanUtils.copyProperties(resp, m);
		Long orderId = order.getId();
		this.orderService.savePrepayInfo(m, orderId);

		if (!this.validateUnifiedOrderResponse(resp)) {
			logger.error("错误的响应信息!\n{}", resp);
			this.orderService.updateOrderStatus(orderId, OrderStatus.FAIL);
			OrderResultInfo info = new OrderResultInfo();
			info.setTimeEnd(new Date());
			info.setOpenId(order.getConsumer().getOpenId());
			info.setReturnCode("ERROR");
			String reason = "发起订单请求出错！";
			info.setDesc(reason);
			info.setErrCodeDes(reason);
			this.orderService.savePayResultInfo(info, orderId);
			ErrorOutbound errMsg = new ErrorOutbound(reason);
			this.chatService.sendPrivacyMsg(errMsg, order.getConsumer().getId(), null);
			return null;
		}
		PrepayInfoDTO rs = generatePrepayInfo(resp);

		this.orderService.updateOrderStatus(orderId, OrderStatus.DELIVERED);
		PrepayMsgDTO prepayMsg = new PrepayMsgDTO();
		prepayMsg.setCreationDate(new Date());
		prepayMsg.setPrepayInfo(rs);
		prepayMsg.setSentDate(new Date());
		Long msgId = order.getMsgId();
		if (msgId != null) {
			ChatMsg chatMsg = this.commonDao.get(ChatMsg.class, msgId);
			prepayMsg.setClientMsgId(chatMsg.getClientMsgId());
		}
		String desc = "请为你消费的产品支付！总价:" + (float) order.getTotalFee() / 100 + " 元！";
		prepayMsg.setDesc(desc);

		this.chatService.sendPrivacyMsg(prepayMsg, order.getConsumer().getId(), null);
		return rs;
	}

	private boolean validateUnifiedOrderResponse(UnifiedOrderResponse resp) {
		if (resp == null) {
			return false;
		}
		if (!"SUCCESS".equals(resp.getReturnCode())) {
			return false;
		}
		if (!"SUCCESS".equals(resp.getResultCode())) {
			return false;
		}
		return !StringUtils.isBlank(resp.getPrepayId());
	}

	private PrepayInfoDTO generatePrepayInfo(UnifiedOrderResponse response) {
		PrepayInfoDTO rs = new PrepayInfoDTO();
		String appId = response.getAppid();
		String timeStamp = WxUtils.generateTimestamp();
		String nonceStr = WxUtils.generateNonce(32);
		String packageStr = "prepay_id=" + response.getPrepayId();
		rs.setAppId(appId);
		rs.setTimeStamp(timeStamp);
		rs.setNonceStr(nonceStr);
		rs.setPackageStr(packageStr);
		List<NameValuePair> parameters = new ArrayList<>();
		try {
			parameters.add(WxUtils.generateNVPair("appId", appId));
			parameters.add(WxUtils.generateNVPair("timeStamp", timeStamp));
			parameters.add(WxUtils.generateNVPair("nonceStr", nonceStr));
			parameters.add(WxUtils.generateNVPair("package", packageStr));
			parameters.add(WxUtils.generateNVPair("signType", rs.getSignType()));
		} catch (Exception ex) {
			logger.error("计算签名出错！", ex);
			return null;
		}
		String paySign = WxUtils.generatePaySign(parameters, globalConfig.getPayApiKey());
		rs.setPaySign(paySign);
		return rs;
	}
}
