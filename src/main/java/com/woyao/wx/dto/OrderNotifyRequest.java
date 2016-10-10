package com.woyao.wx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "OrderNotify")
public class OrderNotifyRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "return_code", required = true)
	private String returnCode;

	@XmlElement(name = "return_msg", required = true)
	private String returnMsg;

	@XmlElement(name = "appid")
	private String appId;

	@XmlElement(name = "mch_id")
	private String mchId;

	@XmlElement(name = "device_info")
	private String deviceInfo;

	@XmlElement(name = "nonce_str", required = true)
	private String nonce;

	@XmlElement(name = "sign", required = true)
	private String sign;

	@XmlElement(name = "result_code", required = true)
	private String resultCode;

	@XmlElement(name = "err_code")
	private String errCode;

	@XmlElement(name = "err_code_des")
	private String errCodeDes;

	@XmlElement(name = "openid")
	private String openId;

	@XmlElement(name = "is_subscribe")
	private String isSubscribe;

	@XmlElement(name = "trade_type")
	private String tradeType;

	@XmlElement(name = "bank_type")
	private String bankType;

	@XmlElement(name = "total_fee")
	private int totalFee;

	@XmlElement(name = "settlement_total_fee")
	private Integer settlementTotalFee;

	@XmlElement(name = "fee_type")
	private String feeType;

	@XmlElement(name = "cash_fee")
	private int cashFee;

	@XmlElement(name = "cash_fee_type")
	private String cashFeeType;

	@XmlElement(name = "coupon_fee")
	private Integer couponFee;

	@XmlElement(name = "coupon_count")
	private Integer couponCount;

	@XmlElement(name = "coupon_type_0")
	private String couponType0;
	@XmlElement(name = "coupon_type_1")
	private String couponType1;
	@XmlElement(name = "coupon_type_2")
	private String couponType2;
	@XmlElement(name = "coupon_type_3")
	private String couponType3;
	@XmlElement(name = "coupon_type_4")
	private String couponType4;
	@XmlElement(name = "coupon_type_5")
	private String couponType5;

	@XmlElement(name = "coupon_id_0")
	private String couponId0;
	@XmlElement(name = "coupon_id_1")
	private String couponId1;
	@XmlElement(name = "coupon_id_2")
	private String couponId2;
	@XmlElement(name = "coupon_id_3")
	private String couponId3;
	@XmlElement(name = "coupon_id_4")
	private String couponId4;
	@XmlElement(name = "coupon_id_5")
	private String couponId5;

	@XmlElement(name = "coupon_fee_0")
	private String couponFee0;
	@XmlElement(name = "coupon_fee_1")
	private String couponFee1;
	@XmlElement(name = "coupon_fee_2")
	private String couponFee2;
	@XmlElement(name = "coupon_fee_3")
	private String couponFee3;
	@XmlElement(name = "coupon_fee_4")
	private String couponFee4;
	@XmlElement(name = "coupon_fee_5")
	private String couponFee5;

	@XmlElement(name = "transaction_id")
	private String transactionId;

	@XmlElement(name = "out_trade_no")
	private String outTradeNo;

	@XmlElement(name = "attach")
	private String attach;

	@XmlElement(name = "time_end")
	private String timeEnd;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public int getCashFee() {
		return cashFee;
	}

	public void setCashFee(int cashFee) {
		this.cashFee = cashFee;
	}

	public String getCashFeeType() {
		return cashFeeType;
	}

	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public String getCouponType0() {
		return couponType0;
	}

	public void setCouponType0(String couponType0) {
		this.couponType0 = couponType0;
	}

	public String getCouponType1() {
		return couponType1;
	}

	public void setCouponType1(String couponType1) {
		this.couponType1 = couponType1;
	}

	public String getCouponType2() {
		return couponType2;
	}

	public void setCouponType2(String couponType2) {
		this.couponType2 = couponType2;
	}

	public String getCouponType3() {
		return couponType3;
	}

	public void setCouponType3(String couponType3) {
		this.couponType3 = couponType3;
	}

	public String getCouponType4() {
		return couponType4;
	}

	public void setCouponType4(String couponType4) {
		this.couponType4 = couponType4;
	}

	public String getCouponType5() {
		return couponType5;
	}

	public void setCouponType5(String couponType5) {
		this.couponType5 = couponType5;
	}

	public String getCouponId0() {
		return couponId0;
	}

	public void setCouponId0(String couponId0) {
		this.couponId0 = couponId0;
	}

	public String getCouponId1() {
		return couponId1;
	}

	public void setCouponId1(String couponId1) {
		this.couponId1 = couponId1;
	}

	public String getCouponId2() {
		return couponId2;
	}

	public void setCouponId2(String couponId2) {
		this.couponId2 = couponId2;
	}

	public String getCouponId3() {
		return couponId3;
	}

	public void setCouponId3(String couponId3) {
		this.couponId3 = couponId3;
	}

	public String getCouponId4() {
		return couponId4;
	}

	public void setCouponId4(String couponId4) {
		this.couponId4 = couponId4;
	}

	public String getCouponId5() {
		return couponId5;
	}

	public void setCouponId5(String couponId5) {
		this.couponId5 = couponId5;
	}

	public String getCouponFee0() {
		return couponFee0;
	}

	public void setCouponFee0(String couponFee0) {
		this.couponFee0 = couponFee0;
	}

	public String getCouponFee1() {
		return couponFee1;
	}

	public void setCouponFee1(String couponFee1) {
		this.couponFee1 = couponFee1;
	}

	public String getCouponFee2() {
		return couponFee2;
	}

	public void setCouponFee2(String couponFee2) {
		this.couponFee2 = couponFee2;
	}

	public String getCouponFee3() {
		return couponFee3;
	}

	public void setCouponFee3(String couponFee3) {
		this.couponFee3 = couponFee3;
	}

	public String getCouponFee4() {
		return couponFee4;
	}

	public void setCouponFee4(String couponFee4) {
		this.couponFee4 = couponFee4;
	}

	public String getCouponFee5() {
		return couponFee5;
	}

	public void setCouponFee5(String couponFee5) {
		this.couponFee5 = couponFee5;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

}
