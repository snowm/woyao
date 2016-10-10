package com.woyao.wx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UnifiedOrderRequest implements Serializable {

	/**
	 * 统一下单请求参数
	 */
	private static final long serialVersionUID = 1L;

	@XmlCDATA
	@XmlElement(name = "appid", required = true)
	private String appid;// 公众账号ID

	@XmlCDATA
	@XmlElement(name = "mch_id", required = true)
	private String mchId;// 商户号

	@XmlCDATA
	@XmlElement(name = "device_info")
	private String deviceInfo;// 设备号

	@XmlCDATA
	@XmlElement(name = "nonce_str", required = true)
	private String nonceStr;// 设备号

	@XmlCDATA
	@XmlElement(name = "sign", required = true)
	private String sign;// 签名

	@XmlCDATA
	@XmlElement(name = "body", required = true)
	private String body;// 商品描述

	@XmlCDATA
	@XmlElement(name = "detail")
	private String detail;// 商品详情

	@XmlCDATA
	@XmlElement(name = "attach")
	private String attach;// 附加数据

	@XmlCDATA
	@XmlElement(name = "out_trade_no", required = true)
	private String outTradeNo;// 商户订单号

	@XmlCDATA
	@XmlElement(name = "fee_type")
	private String feeType;// 货币类型

	@XmlCDATA
	@XmlElement(name = "total_fee", required = true)
	private int totalFee;// 总金额

	@XmlCDATA
	@XmlElement(name = "spbill_create_ip", required = true)
	private String spbillCreateIp;// 终端IP

	@XmlCDATA
	@XmlElement(name = "time_start")
	private String timeStart;// 交易起始时间

	@XmlCDATA
	@XmlElement(name = "time_expire")
	private String timeExpire;// 交易结束时间

	@XmlCDATA
	@XmlElement(name = "goods_tag")
	private String goodsTag;// 商品标记

	@XmlCDATA
	@XmlElement(name = "notify_url", required = true)
	private String notifyUrl;// 通知地址

	@XmlCDATA
	@XmlElement(name = "trade_type", required = true)
	private String tradeType;// 交易类型

	@XmlCDATA
	@XmlElement(name = "product_id")
	private String productId;// 商品ID

	@XmlCDATA
	@XmlElement(name = "limit_pay")
	private String limitPay;// 指定支付方式

	@XmlCDATA
	@XmlElement(name = "openid")
	private String openid;// 用户标识

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
