package com.woyao.admin.dto.product;

import java.util.List;

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
		
	private List<ProductDTO> products;//产品	

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

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "OrderDTO [version=" + version + ", consumerId=" + consumerId + ", consumerName=" + consumerName + ", toProfileId="
				+ toProfileId + ", toProfileName=" + toProfileName + ", prepayInfoId=" + prepayInfoId + ", statusId=" + statusId
				+ ", totalFee=" + totalFee + ", products=" + products + "]";
	}

	
	

	
}
