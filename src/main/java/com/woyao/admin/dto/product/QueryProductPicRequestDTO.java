package com.woyao.admin.dto.product;

import com.woyao.PaginationQueryRequestDTO;

public class QueryProductPicRequestDTO extends PaginationQueryRequestDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer productId;
	
	private Integer picId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	
}
