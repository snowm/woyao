package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class ProductDTO extends BasePKDTO {

	private static final long serialVersionUID = 1L;

	private String code;

	private String name;

	private String description;

	private String mainPic;
	
	private Long mainPicId;
	
	private int typeId;
	
	private Long shopId;
	
	private String shopName;
	
	private int unitPrice;
	
	private int quantity;
	
	private long totalFee;

	public long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(long totalFee) {
		this.totalFee = totalFee;
	}

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

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Long getMainPicId() {
		return mainPicId;
	}

	public void setMainPicId(Long mainPicId) {
		this.mainPicId = mainPicId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ProductDTO [code=" + code + ", name=" + name + ", description=" + description + ", mainPic=" + mainPic + ", mainPicId="
				+ mainPicId + ", typeId=" + typeId + ", shopId=" + shopId + ", shopName=" + shopName + ", unitPrice=" + unitPrice + "]";
	}
	

}
