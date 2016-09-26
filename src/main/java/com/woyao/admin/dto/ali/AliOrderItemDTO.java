package com.woyao.admin.dto.ali;

import com.woyao.admin.dto.BasePKDTO;
import com.woyao.admin.dto.product.SkuDTO;

public class AliOrderItemDTO extends BasePKDTO {

	private static final long serialVersionUID = 1L;

	private Long orderId;
	
	private SkuDTO sku;
	
	private int quantity;
	
	private int sellingPrice;
	
	private AliOrderItemTypeDTO orderItemType;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public SkuDTO getSku() {
		return sku;
	}

	public void setSku(SkuDTO sku) {
		this.sku = sku;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public AliOrderItemTypeDTO getOrderItemType() {
		return orderItemType;
	}

	public void setOrderItemType(AliOrderItemTypeDTO orderItemType) {
		this.orderItemType = orderItemType;
	}
	
}
