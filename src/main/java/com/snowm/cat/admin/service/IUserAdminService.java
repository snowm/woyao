package com.snowm.cat.admin.service;

import com.snowm.cat.admin.dto.profile.ProfileDTO;
import com.snowm.security.profile.domain.Profile;
import com.snowm.utils.query.PaginationBean;

public interface IUserAdminService extends IAdminService<Profile, ProfileDTO> {

	PaginationBean<ProfileDTO> query(String name, Boolean enabled, long pageNumber, int pageSize);

}
