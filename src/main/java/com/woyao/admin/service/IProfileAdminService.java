package com.woyao.admin.service;

import com.snowm.security.profile.domain.Profile;
import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryProfileRequestDTO;
import com.woyao.admin.dto.profile.ProfileDTO;

public interface IProfileAdminService extends IAdminService<Profile, ProfileDTO>{

	PaginationBean<ProfileDTO> query(QueryProfileRequestDTO request);
	
	/**
	 * 重置密码方法
	 */
	
	boolean resetProfilePwd(Long shopId);
}
