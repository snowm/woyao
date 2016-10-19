package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class OrderItemDTO extends BasePKDTO{

	private static final long serialVersionUID = 1L;
	
	private ProductDTO pdto;
	
	private OrderDTO odto;

	public ProductDTO getPdto() {
		return pdto;
	}

	public void setPdto(ProductDTO pdto) {
		this.pdto = pdto;
	}

	public OrderDTO getOdto() {
		return odto;
	}

	public void setOdto(OrderDTO odto) {
		this.odto = odto;
	}
	
	

}
