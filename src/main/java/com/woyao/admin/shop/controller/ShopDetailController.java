package com.woyao.admin.shop.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.domain.Shop;

@Controller
@RequestMapping(value = "/shop/admin/detail")
public class ShopDetailController extends AbstractBaseController<Shop, ShopDTO> {

	@Resource(name = "shopAdminService")
	private IShopAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopDTO query(HttpServletRequest httpRequest) {
		long shopId = SessionUtils.getShopId(httpRequest.getSession());
		ShopDTO rs = this.service.get(shopId);
		return rs;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopDTO saveOrUpdate(ShopDTO dto, HttpServletRequest httpRequest) {
		long shopId = SessionUtils.getShopId(httpRequest.getSession());
		dto.setId(shopId);
		return this.service.update(dto);
	}

	@Resource
	@Override
	public void setBaseService(@Qualifier("shopAdminService") IAdminService<Shop, ShopDTO> baseService) {
		this.baseService = baseService;
	}

}
