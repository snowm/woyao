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
import com.woyao.admin.dto.product.ProductPicDTO;
import com.woyao.admin.dto.product.QueryProductPicRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProductPicAdminService;
import com.woyao.domain.product.ProductPic;

@Controller
@RequestMapping(value = "/admin/productpic")
public class ProductPicAdminController extends AbstractBaseController<ProductPic, ProductPicDTO> {

	@Resource(name = "productPicAdminService")
	private IProductPicAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProductPicDTO> query(QueryProductPicRequestDTO request) {
		PaginationBean<ProductPicDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProductPicDTO saveOrUpdate(@RequestBody ProductPicDTO dto) {
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("productPicAdminService") IAdminService<ProductPic, ProductPicDTO> baseService) {
		this.baseService = baseService;
	}

}
