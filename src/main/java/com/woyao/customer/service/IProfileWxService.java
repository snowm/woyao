package com.woyao.customer.service;

import com.woyao.customer.dto.ProfileDTO;

public interface IProfileWxService {

	ProfileDTO getByOpenId(String openId);

	ProfileDTO saveProfileInfo(ProfileDTO dto);

	ProfileDTO getById(long id);

}
