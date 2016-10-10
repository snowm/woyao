package com.woyao.customer.dto;

import com.snowm.security.profile.domain.Gender;
import com.woyao.PaginationQueryRequestDTO;

public class ChatterQueryRequest extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 1L;

	private Gender gender;

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
