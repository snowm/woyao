package com.woyao.customer.dto;

import org.springframework.beans.BeanUtils;

import com.woyao.domain.profile.ProfileWX;

public abstract class ChatterConverter {

	public static ChatterDTO toDTO(ProfileWX chatter){
		ChatterDTO dto = new ChatterDTO();
		BeanUtils.copyProperties(chatter, dto);
		return dto;
	}
}
