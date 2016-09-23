package com.snowm.cat.admin.dto.product;

import com.snowm.cat.admin.dto.BasePKDTO;

public class SkuDTO extends BasePKDTO {

	private static final long serialVersionUID = 1L;

	private ProductDTO product;
	
	private String size;
	
	private String colorCode;
	
	private String colorDesc;

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getColorDesc() {
		return colorDesc;
	}

	public void setColorDesc(String colorDesc) {
		this.colorDesc = colorDesc;
	}
	
}
