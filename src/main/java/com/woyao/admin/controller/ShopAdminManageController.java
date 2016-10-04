package com.woyao.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopAdmin")
public class ShopAdminManageController {

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public String rootHome(Model model) {
		return "shopAdminIndex";
	}

}
