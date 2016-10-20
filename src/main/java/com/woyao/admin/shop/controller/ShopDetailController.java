package com.woyao.admin.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyao.admin.dto.product.ShopDTO;

@Controller
@RequestMapping(value = "/shop/admin/detail")
public class ShopDetailController{

	@Autowired
	private ShopRoot shopRoot;

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopDTO query() {		
		return 	shopRoot.getCurrentShop();
	}
	
}
