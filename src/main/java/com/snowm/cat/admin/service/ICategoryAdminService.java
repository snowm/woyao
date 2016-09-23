package com.snowm.cat.admin.service;

import com.snowm.cat.admin.dto.product.CategoryDTO;
import com.snowm.cat.admin.dto.product.QueryCategoriesRequestDTO;
import com.snowm.cat.domain.product.Category;
import com.snowm.utils.query.PaginationBean;

public interface ICategoryAdminService extends IAdminService<Category, CategoryDTO> {

	PaginationBean<CategoryDTO> query(QueryCategoriesRequestDTO queryRequest);

}
