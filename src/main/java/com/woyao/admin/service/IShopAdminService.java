package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryShopsRequestDTO;
import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.domain.Shop;

public interface IShopAdminService extends IAdminService<Shop, ShopDTO>{

	PaginationBean<ShopDTO> query(QueryShopsRequestDTO request);
	
}
