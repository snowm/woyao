package com.woyao.wx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;
@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderReportRequest implements Serializable{

	/**交易保障
	 * 商户在调用微信支付提供的相关接口时，会得到微信支付返回的相关信息以及获得整个接口的响应时间。为提高整体的服务水平，协助商户一起提高服务质量，
	 * 微信支付提供了相关接口调用耗时和返回信息的主动上报接口，微信支付可以根据商户侧上报的数据进一步优化网络部署，
	 * 完善服务监控，和商户更好的协作为用户提供更好的业务体验。 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlCDATA
	@XmlElement(name = "appid", required = true)
	private String appid ;//公众账号ID 
	
	@XmlCDATA
	@XmlElement(name = "mch_id", required = true)
	private String mchId ;//商户号 
	
	@XmlCDATA
	@XmlElement(name = "device_info")
	private String deviceInfo;//设备号
	
	@XmlCDATA
	@XmlElement(name = "nonce_str", required = true)
	private String nonceStr ;//随机字符串
	
	@XmlCDATA
	@XmlElement(name = "sign", required = true)
	private String sign ;//签名
	
	@XmlCDATA
	@XmlElement(name = "interface_url", required = true)
	private String interfaceUrl ;//接口URL
	
	@XmlCDATA
	@XmlElement(name = "execute_time", required = true)
	private int executeTime ;//接口耗时
	
	@XmlCDATA
	@XmlElement(name = "return_code", required = true)
	private int returnCode ;//返回状态码
	
	@XmlCDATA
	@XmlElement(name = "return_msg")
	private int returnMsg ;//返回信息
	
	@XmlCDATA
	@XmlElement(name = "result_code", required = true)
	private int resultCode ;//业务结果
	
	@XmlCDATA
	@XmlElement(name = "err_code")
	private int errCode ;//错误代码
	
	@XmlCDATA
	@XmlElement(name = "err_code_des")
	private int errCodeDes ;//错误代码描述
	
	@XmlCDATA
	@XmlElement(name = "out_trade_no")
	private int outTradeNo ;//商户订单号
	
	@XmlCDATA
	@XmlElement(name = "user_ip", required = true)
	private int userIp ;//访问接口IP
	
	@XmlCDATA
	@XmlElement(name = "time")
	private int time ;//商户上报时间

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

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public int getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(int executeTime) {
		this.executeTime = executeTime;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public int getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(int returnMsg) {
		this.returnMsg = returnMsg;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public int getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(int errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public int getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(int outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public int getUserIp() {
		return userIp;
	}

	public void setUserIp(int userIp) {
		this.userIp = userIp;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
	
	

}
