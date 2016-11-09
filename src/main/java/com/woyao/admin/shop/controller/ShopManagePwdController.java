package com.woyao.admin.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.security.profile.domain.Profile;
import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.profile.ProfileDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProfileAdminService;
import com.woyao.customer.chat.SessionUtils;

@Controller
@RequestMapping(value = "/shop/admin/manager")
public class ShopManagePwdController extends AbstractBaseController<Profile, ProfileDTO>{
	
	@Resource(name = "profileAdminService")
	private IProfileAdminService service;
	
	@RequestMapping(value = {"","/"},method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Integer validatorPwd(@RequestParam(value = "oldPwd", required = true) String oldPwd,
			@RequestParam(value = "newPwd", required = false) String newPwd,
			@RequestParam(value = "againPwd", required = false) String againPwd, HttpServletRequest httpRequest) {
		long shopId = SessionUtils.getShopId(httpRequest.getSession());
		if (!newPwd.trim().isEmpty() && !againPwd.trim().isEmpty()) {
			return this.service.updataProfilePwd(shopId, oldPwd, newPwd, againPwd);
		}
		return null;
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("profileAdminService") IAdminService<Profile, ProfileDTO> baseService) {
		this.baseService = baseService;
	}
	
}
