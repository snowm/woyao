package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyao.admin.dto.profile.ProfileDTO;
import com.woyao.admin.dto.profile.QueryUsersRequestDTO;
import com.woyao.admin.service.IUserAdminService;
import com.snowm.security.profile.domain.Gender;
import com.snowm.utils.query.PaginationBean;

@Controller
@RequestMapping(value = "/admin/user")
public class UserController {

	@Resource(name = "userAdminService")
	private IUserAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProfileDTO> query(QueryUsersRequestDTO request) {
		PaginationBean<ProfileDTO> result = this.service.query(request.getName(), request.getEnabled(), request.getPageNumber(),
				request.getPageSize());
		return result;
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void delete(@PathVariable("id") long id) {
		this.service.delete(id);
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProfileDTO get(@PathVariable("id") long id) {
		ProfileDTO dto = this.service.get(id);
		return dto;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT, RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProfileDTO saveOrUpdate(@RequestBody ProfileDTO dto) {
		dto.setGender(Gender.MALE);
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
}
