package com.woyao.admin.dto.product;

import com.woyao.PaginationQueryRequestDTO;

public class QueryProductsRequestDTO extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 6065375426171420402L;

	private Long categoryId;

	private String name;

	private Boolean enabled = true;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
