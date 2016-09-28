package com.woyao.customer.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.customer.dto.ShopDTO;

@Controller
@RequestMapping(value = "/m")
public class MobileController {

	@Resource(name = "mobileService")
	private MobileService mobileService;

	@RequestMapping(value = { "/shop" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(PaginationQueryRequestDTO queryRequest) {
		return this.mobileService.findShop(queryRequest.getPageNumber(), queryRequest.getPageSize());
	}

}
