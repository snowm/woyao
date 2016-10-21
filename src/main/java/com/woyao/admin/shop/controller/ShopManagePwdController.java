package com.woyao.admin.shop.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.woyao.domain.Shop;

@Controller
@RequestMapping(value = "/shop/admin/manage")
public class ShopManagePwdController extends AbstractBaseController<Shop, ShopDTO>{

	@Autowired
	private ShopRoot shopRoot;
	
	@Resource(name = "shopAdminService")
	private IShopAdminService service;
	
	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopDTO update(ShopDTO dto) {	
		dto.setId(shopRoot.getCurrentShop().getId());
		if (dto.getId() != null) {
			return this.service.update(dto);
		}
		return null;
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("shopAdminService") IAdminService<Shop, ShopDTO> baseService) {
		this.baseService = baseService;
	}
	
}
