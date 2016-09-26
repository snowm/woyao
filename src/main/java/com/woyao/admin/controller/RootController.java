package com.woyao.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/")
public class RootController {

	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	public String rootHome(Model model) {
		return "redirect:/admin/";
	}

	@RequestMapping(value = "loginPage.html", method = RequestMethod.GET)
	public String loginPage(Model model) {
		return "login";
	}

	@RequestMapping(value = "customerIndex.html", method = RequestMethod.GET)
	public String customerIndexPage(Model model) {
		return "customerIndex";
	}

	@RequestMapping(value = "shopIndex.html", method = RequestMethod.GET)
	public String shopIndexPage(Model model) {
		return "shopIndex";
	}

	@RequestMapping(value = "adminIndex.html", method = RequestMethod.GET)
	public String adminIndexPage(Model model) {
		return "adminIndex";
	}

	@RequestMapping(value = "shopAdminIndex.html", method = RequestMethod.GET)
	public String shopAdminIndexPage(Model model) {
		return "shopAdminIndex";
	}
}
