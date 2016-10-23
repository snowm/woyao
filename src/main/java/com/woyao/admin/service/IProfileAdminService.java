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
	
	/**
	 * 酒吧后台修改管理员密码,修改密码
	 */
	Integer updataProfilePwd(String oldPwd,String newPwd,String againPwd);

}
