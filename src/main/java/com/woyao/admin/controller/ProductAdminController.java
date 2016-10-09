package com.woyao.admin.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryProductsRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProductAdminService;
import com.woyao.domain.product.Product;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductAdminController extends AbstractBaseController<Product, ProductDTO> {

	@Autowired
	private IProductAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProductDTO> query(QueryProductsRequestDTO request) {
		PaginationBean<ProductDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProductDTO saveOrUpdate(ProductDTO dto) {
		dto.setCode(UUID.randomUUID().toString());
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
