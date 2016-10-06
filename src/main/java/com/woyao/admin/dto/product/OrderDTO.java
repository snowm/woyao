package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class OrderDTO extends BasePKDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int version;//版本
	
	private long consumerId;//付款Id
	
	private String consumerName;//付款名称
	
	private long toProfileId;//收货Id
	
	private String toProfileName;//收货名称
	
	private long prepayInfoId;//预付Id
	
	private Integer statusId;//成功或否
	
	private Integer totalFee;//总费用
	
	private long productId;//商品Id
	
	private String productName;//商品名称
	
	private Integer productType;//商品类型
	
	private long shopId;//商店Id
	
	private String shopName;//商店名称
	
	private String productdescription;//商品描述
	
	private long productunitPrice;//商品单价
	
	private long productquantity;//商品数量

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

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getProductdescription() {
		return productdescription;
	}

	public void setProductdescription(String productdescription) {
		this.productdescription = productdescription;
	}

	public long getProductunitPrice() {
		return productunitPrice;
	}

	public void setProductunitPrice(long productunitPrice) {
		this.productunitPrice = productunitPrice;
	}

	public long getProductquantity() {
		return productquantity;
	}

	public void setProductquantity(long productquantity) {
		this.productquantity = productquantity;
	}
	
	

}
