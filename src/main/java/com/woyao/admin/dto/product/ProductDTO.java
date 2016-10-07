package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class ProductDTO extends BasePKDTO {

	private static final long serialVersionUID = 1L;

	private String code;

	private String name;

	private String description;

	private String mainPic;
	
	private Long mainPicId;
	
	private Integer typeId;
	
	private long shopId;
	
	private String shopName;
	
	private long unitPrice;
	
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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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

	public long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Long getMainPicId() {
		return mainPicId;
	}

	public void setMainPicId(Long mainPicId) {
		this.mainPicId = mainPicId;
	}

	@Override
	public String toString() {
		return "ProductDTO [code=" + code + ", name=" + name + ", description=" + description + ", mainPic=" + mainPic + ", mainPicId="
				+ mainPicId + ", typeId=" + typeId + ", shopId=" + shopId + ", shopName=" + shopName + ", unitPrice=" + unitPrice + "]";
	}
	

}
