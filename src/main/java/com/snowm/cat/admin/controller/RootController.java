package com.snowm.cat.admin.controller;

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
}
