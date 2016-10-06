package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.profile.ProfileWXDTO;
import com.woyao.admin.dto.profile.QueryProfileWXRequestDTO;
import com.woyao.domain.profile.ProfileWX;

public interface IProfileWXAdminService extends IAdminService<ProfileWX, ProfileWXDTO>{

	PaginationBean<ProfileWXDTO> query(QueryProfileWXRequestDTO request);
	
}
