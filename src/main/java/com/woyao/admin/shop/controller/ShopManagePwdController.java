package com.woyao.admin.shop.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.security.profile.domain.Profile;
import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.profile.ProfileDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProfileAdminService;

@Controller
@RequestMapping(value = "/shop/admin/manager")
public class ShopManagePwdController extends AbstractBaseController<Profile, ProfileDTO>{
	
	
	@Resource(name = "profileAdminService")
	private IProfileAdminService service;
	
	@RequestMapping(value = {""}, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public boolean validatorPwd(String oldPwd) {	
		return this.service.oldPassword(oldPwd);
	}
	
	@RequestMapping(value = {"/update"}, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public boolean updatePwd(String newPwd,String againPwd) {
		return this.service.updatePassword(newPwd, againPwd);
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("profileAdminService") IAdminService<Profile, ProfileDTO> baseService) {
		this.baseService = baseService;
	}
	
}
