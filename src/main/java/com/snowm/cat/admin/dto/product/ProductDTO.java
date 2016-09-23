package com.snowm.cat.admin.dto.product;

import com.snowm.cat.admin.dto.BasePKDTO;

public class ProductDTO extends BasePKDTO {

	private static final long serialVersionUID = 1L;

	private String code;

	private String name;

	private String description;

	private String mainPic;

	private CategoryDTO category;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

}
