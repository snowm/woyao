package com.woyao.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/s")
public class ShopController {

	@RequestMapping(value = { "/", "" })
	public String index() {
		return "shopIndex";
	}

}
