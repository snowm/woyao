package com.woyao.customer.dto;

import com.snowm.security.profile.domain.Gender;
import com.woyao.PaginationQueryRequestDTO;

public class ChatterPaginationQueryRequest extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 1L;

	private long shopId;

	private Gender gender;

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
