package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.admin.dto.product.QueryOrderRequestDTO;
import com.woyao.admin.dto.purchase.OrderDTO;
import com.woyao.admin.shop.dto.SMSParamsDTO;
import com.woyao.admin.shop.dto.ShopOrderDTO;
import com.woyao.domain.Shop;
import com.woyao.domain.purchase.Order;

public interface IOrderAdminService extends IAdminService<Order, OrderDTO> {

	PaginationBean<OrderDTO> query(QueryOrderRequestDTO queryRequest);

	/*OrderDTO transferToSimpleDTO(Order m);*/
	
	Order get(Long OrderId);

	ShopOrderDTO getYearOrder(Long shopId);
	
	SMSParamsDTO getShopDailyReport(Shop shop);

	PaginationBean<SMSParamsDTO> listShopDailyReports(PaginationQueryRequestDTO request);
	
}
