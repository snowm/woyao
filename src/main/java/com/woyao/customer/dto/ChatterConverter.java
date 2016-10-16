package com.woyao.customer.dto;

import org.springframework.beans.BeanUtils;

import com.woyao.domain.profile.ProfileWX;

public abstract class ChatterConverter {

	public static ProfileDTO toDTO(ProfileWX chatter){
		ProfileDTO dto = new ProfileDTO();
		BeanUtils.copyProperties(chatter, dto);
		return dto;
	}
}
