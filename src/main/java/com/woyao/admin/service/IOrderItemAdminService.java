package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.OrderDTO;
import com.woyao.admin.dto.product.QueryOrderItemRequestDTO;
import com.woyao.domain.purchase.Order;

public interface IOrderItemAdminService extends IAdminService<Order, OrderDTO>{

	PaginationBean<OrderDTO> query(QueryOrderItemRequestDTO request);
	
	OrderDTO queryItem(QueryOrderItemRequestDTO request);
	
}
