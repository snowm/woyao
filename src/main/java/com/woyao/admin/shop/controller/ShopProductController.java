package com.woyao.admin.shop.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryProductsRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProductAdminService;
import com.woyao.admin.shop.ShopRootController;
import com.woyao.domain.product.Product;

@Controller
@RequestMapping(value = "/shop/admin/product")
public class ShopProductController extends AbstractBaseController<Product, ProductDTO> {

	@Autowired
	private IProductAdminService service;
	
	@Autowired
	private ShopRoot shopRoot;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProductDTO> query(QueryProductsRequestDTO request) {
		Long shopId=shopRoot.getCurrentShop().getId();
		request.setShopId(shopId);
		PaginationBean<ProductDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProductDTO saveOrUpdate(ProductDTO dto) {
		dto.setShopId(shopRoot.getCurrentShop().getId());
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Autowired
	@Override
	public void setBaseService(@Qualifier("productAdminService") IAdminService<Product, ProductDTO> baseService) {
		this.baseService = baseService;
	}

}
