package com.woyao.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@RequestMapping(value = {""}, method = RequestMethod.GET)
	public String adminHome1(Model model) {
		return "redirect:/admin/";
	}
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String rootHome(Model model) {
		model.addAttribute("rs", "test");
		return "index";
	}

}
