package com.woyao.admin.dto.product;

import java.util.List;

import com.woyao.admin.dto.BasePKDTO;

public class OrderDTO extends BasePKDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int version;// 版本

	private long consumerId;// 付款Id

	private String consumerName;// 付款名称

	private long toProfileId;// 收货Id

	private String toProfileName;// 收货名称

	private long prepayInfoId;// 预付Id

	private Integer statusId;// 成功或否

	private int totalFee;// 总费用
	
	private String prepayId;//预付款Id

	private String appid;// 公众账号ID
	
	private String mchId;// 商户号
	
	private String resultCode;// 业务结果
	
	private String errCode;// 错误代码
	
	private String errCodeDes;// 错误代码描述
	
	private String tradeType;// 交易类型
	
	private String codeUrl;// 二维码链接
	
	private String spbillCreateIp;
	
	private Long msgId;//消息Id
	
	private String msgpic;//消息图片
	
	private String orderNo;
	
	private List<ProductDTO> products;// 产品


	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(long consumerId) {
		this.consumerId = consumerId;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public long getToProfileId() {
		return toProfileId;
	}

	public void setToProfileId(long toProfileId) {
		this.toProfileId = toProfileId;
	}

	public String getToProfileName() {
		return toProfileName;
	}

	public void setToProfileName(String toProfileName) {
		this.toProfileName = toProfileName;
	}

	public long getPrepayInfoId() {
		return prepayInfoId;
	}

	public void setPrepayInfoId(long prepayInfoId) {
		this.prepayInfoId = prepayInfoId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
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

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getMsgpic() {
		return msgpic;
	}

	public void setMsgpic(String msgpic) {
		this.msgpic = msgpic;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	

}
