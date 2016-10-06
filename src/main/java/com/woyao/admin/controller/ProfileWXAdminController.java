package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.profile.ProfileWXDTO;
import com.woyao.admin.dto.profile.QueryProfileWXRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProfileWXAdminService;
import com.woyao.domain.profile.ProfileWX;

@Controller
@RequestMapping(value = "/admin/profileWX")
public class ProfileWXAdminController extends AbstractBaseController<ProfileWX, ProfileWXDTO> {

	@Resource(name = "profileWXAdminService")
	private IProfileWXAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProfileWXDTO> query(QueryProfileWXRequestDTO request) {
		PaginationBean<ProfileWXDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProfileWXDTO saveOrUpdate(ProfileWXDTO dto) {
		System.out.println(dto);
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("profileWXAdminService") IAdminService<ProfileWX, ProfileWXDTO> baseService) {
		this.baseService = baseService;
	}

}
