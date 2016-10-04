package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryProductsRequestDTO;
import com.woyao.domain.product.Product;

public interface IProductAdminService extends IAdminService<Product, ProductDTO>{
	PaginationBean<ProductDTO> query(QueryProductsRequestDTO request);
}
