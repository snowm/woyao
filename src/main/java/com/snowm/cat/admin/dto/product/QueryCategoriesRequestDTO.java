package com.snowm.cat.admin.dto.product;

import com.snowm.cat.admin.dto.PaginationQueryRequestDTO;

public class QueryCategoriesRequestDTO extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 6065375426171420402L;

	private String name;

	private String code;

	private Boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
