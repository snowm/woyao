package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ProductPicDTO;
import com.woyao.admin.dto.product.QueryProductPicRequestDTO;
import com.woyao.domain.product.ProductPic;

public interface IProductPicAdminService extends IAdminService<ProductPic, ProductPicDTO>{
	PaginationBean<ProductPicDTO> query(QueryProductPicRequestDTO request);
}
