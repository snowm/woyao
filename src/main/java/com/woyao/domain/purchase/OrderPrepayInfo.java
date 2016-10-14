package com.woyao.domain.purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Table(name = "ORDER_PREPAY_INFO")
@TableGenerator(name = "orderPrepayInfoGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "orderPrepayInfo", allocationSize = 1, initialValue = 0)
public class OrderPrepayInfo extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderPrepayInfoGenerator")
	private Long id;

	@Column(name = "PRE_PAY_ID", length = 80)
	private String prepayId;

	@Column(name = "RETURN_CODE", length = 20)
	private String returnCode;// 返回状态码

	@Column(name = "RETURN_MSG", length = 200)
	private String returnMsg;// 返回信息

	@Column(name = "APP_ID", length = 32)
	private String appid;// 公众账号ID

	@Column(name = "MCH_ID", length = 32)
	private String mchId;// 商户号

	@Column(name = "DEVICE_INFO", length = 32)
	private String deviceInfo;// 设备号

	@Column(name = "NONCE_STR", length = 32)
	private String nonceStr;// 随机字符串

	@Column(name = "SIGN", length = 32)
	private String sign;// 签名

	@Column(name = "RESULT_CODE", length = 16)
	private String resultCode;// 业务结果

	@Column(name = "ERR_CODE", length = 32)
	private String errCode;// 错误代码

	@Column(name = "ERR_CODE_DESC", length = 200)
	private String errCodeDes;// 错误代码描述

	@Column(name = "TRADE_TYPE", length = 16)
	private String tradeType;// 交易类型

	@Column(name = "CODE_URL", length = 100)
	private String codeUrl;// 二维码链接

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

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

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

}
