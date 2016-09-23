package com.snowm.cat.admin.dto.product;

import com.snowm.cat.admin.dto.BasePKDTO;

public class CategoryDTO extends BasePKDTO {

	private static final long serialVersionUID = 1L;

	private String name;

	private String code;

	private CategoryDTO parent;

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

	public CategoryDTO getParent() {
		return parent;
	}

	public void setParent(CategoryDTO parent) {
		this.parent = parent;
	}

}
