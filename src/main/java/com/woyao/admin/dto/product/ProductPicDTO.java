package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class ProductPicDTO extends BasePKDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long productId;
	
	private String productName;
	
	private long picId;
	
	private String picUrl;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getPicId() {
		return picId;
	}

	public void setPicId(long picId) {
		this.picId = picId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	

}
