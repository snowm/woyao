package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class ChatRoomDTO extends BasePKDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private long shopId;
	
	private String shopName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	

}
