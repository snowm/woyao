package com.woyao.wx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UnifiedOrderResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlCDATA
	@XmlElement(name = "return_code", required = true)
	private String returnCode;// 返回状态码

	@XmlCDATA
	@XmlElement(name = "return_msg")
	private String returnMsg;// 返回信息

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
	private String nonceStr;// 随机字符串

	@XmlCDATA
	@XmlElement(name = "sign", required = true)
	private String sign;// 签名

	@XmlCDATA
	@XmlElement(name = "result_code", required = true)
	private String resultCode;// 业务结果

	@XmlCDATA
	@XmlElement(name = "err_code")
	private String errCode;// 错误代码

	@XmlCDATA
	@XmlElement(name = "err_code_des")
	private String errCodeDes;// 错误代码描述

	@XmlCDATA
	@XmlElement(name = "trade_type", required = true)
	private String tradeType;// 交易类型

	@XmlCDATA
	@XmlElement(name = "prepay_id", required = true)
	private String prepayId;// 预支付交易会话标识

	@XmlCDATA
	@XmlElement(name = "code_url")
	private String codeUrl;// 二维码链接

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

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
