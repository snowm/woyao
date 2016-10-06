package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.security.profile.domain.Profile;
import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryProfileRequestDTO;
import com.woyao.admin.dto.profile.ProfileDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProfileAdminService;

@Controller
@RequestMapping(value = "/admin/profile")
public class ProfileAdminController extends AbstractBaseController<Profile, ProfileDTO> {

	@Resource(name = "profileAdminService")
	private IProfileAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProfileDTO> query(QueryProfileRequestDTO request) {
		PaginationBean<ProfileDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProfileDTO saveOrUpdate(ProfileDTO dto) {
		System.out.println(dto);
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("profileAdminService") IAdminService<Profile, ProfileDTO> baseService) {
		this.baseService = baseService;
	}

}
