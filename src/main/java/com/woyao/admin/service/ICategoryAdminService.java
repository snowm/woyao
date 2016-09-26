package com.woyao.admin.service;

import com.woyao.admin.dto.product.CategoryDTO;
import com.woyao.admin.dto.product.QueryCategoriesRequestDTO;
import com.woyao.domain.product.Category;
import com.snowm.utils.query.PaginationBean;

public interface ICategoryAdminService extends IAdminService<Category, CategoryDTO> {

	PaginationBean<CategoryDTO> query(QueryCategoriesRequestDTO queryRequest);

}
