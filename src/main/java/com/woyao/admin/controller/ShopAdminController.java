package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryShopsRequestDTO;
import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.domain.Shop;

@Controller
@RequestMapping(value = "/admin/shop")
public class ShopAdminController extends AbstractBaseController<Shop, ShopDTO> {

	@Resource(name = "shopAdminService")
	private IShopAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> query(QueryShopsRequestDTO request) {
		PaginationBean<ShopDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopDTO saveOrUpdate(@RequestBody ShopDTO dto) {
		System.out.println(dto);
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("shopAdminService") IAdminService<Shop, ShopDTO> baseService) {
		this.baseService = baseService;
	}

}
