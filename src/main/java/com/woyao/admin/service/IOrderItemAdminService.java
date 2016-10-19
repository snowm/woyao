package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.OrderItemDTO;
import com.woyao.admin.dto.product.QueryOrderItemRequestDTO;
import com.woyao.domain.purchase.OrderItem;

public interface IOrderItemAdminService extends IAdminService<OrderItem, OrderItemDTO>{

	PaginationBean<OrderItemDTO> query(QueryOrderItemRequestDTO request);
	
}
