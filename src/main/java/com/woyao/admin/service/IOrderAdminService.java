package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryOrderRequestDTO;
import com.woyao.admin.dto.purchase.OrderDTO;
import com.woyao.domain.purchase.Order;

public interface IOrderAdminService extends IAdminService<Order, OrderDTO> {

	PaginationBean<OrderDTO> query(QueryOrderRequestDTO queryRequest);

	/*OrderDTO transferToSimpleDTO(Order m);*/
	
	Order get(Long OrderId);

}
