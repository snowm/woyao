package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyao.admin.dto.product.CategoryDTO;
import com.woyao.admin.dto.product.QueryCategoriesRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.ICategoryAdminService;
import com.woyao.domain.product.Category;
import com.snowm.security.web.exception.NotFoundException;
import com.snowm.utils.query.PaginationBean;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryController extends AbstractBaseController<Category, CategoryDTO> {

	@Resource(name = "categoryAdminService")
	private ICategoryAdminService service;

	public CategoryController() {
		super();
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<CategoryDTO> query(QueryCategoriesRequestDTO request) {
		PaginationBean<CategoryDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "/{id}" }, method = {
			RequestMethod.PUT }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public CategoryDTO update(@RequestBody CategoryDTO dto) {
		CategoryDTO updated = this.baseService.update(dto);
		if (updated == null) {
			throw new NotFoundException(String.format("Category with id : %1$s cound not found!", dto.getId()));
		}
		return updated;
	}

	@RequestMapping(value = { "", "/" }, method = {
			RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public CategoryDTO save(@RequestBody CategoryDTO dto) {
		return this.baseService.create(dto);
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("categoryAdminService") IAdminService<Category, CategoryDTO> baseService) {
		this.baseService = baseService;
	}

}
