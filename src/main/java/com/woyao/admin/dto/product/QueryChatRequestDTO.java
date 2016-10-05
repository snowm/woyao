package com.woyao.admin.dto.product;

import com.woyao.PaginationQueryRequestDTO;

public class QueryChatRequestDTO extends PaginationQueryRequestDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;

	private Boolean deleted = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
