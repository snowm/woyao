package com.woyao.admin.shop.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.product.OrderDTO;
import com.woyao.admin.dto.product.QueryOrderItemRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IOrderItemAdminService;
import com.woyao.admin.shop.dto.ShopOrderDTO;
import com.woyao.domain.purchase.Order;

@Controller
@RequestMapping(value = "/shop/admin/order")
public class OrderItemController extends AbstractBaseController<Order, OrderDTO> {

	@Resource(name = "orderItemService")
	private IOrderItemAdminService service;
	
	@Autowired
	private ShopRoot shopRoot;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<OrderDTO> query(QueryOrderItemRequestDTO request) {
		Long shopId=shopRoot.getCurrentShop().getId();
		request.setShopId(shopId);
		PaginationBean<OrderDTO> result = this.service.query(request);
		return result;
	}
	
	@RequestMapping(value = { "/detil" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public OrderDTO queryitem(QueryOrderItemRequestDTO request) {
		OrderDTO result = this.service.queryItem(request);
		return result;
	}
	
	@RequestMapping(value = { "/main" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopOrderDTO queryitem() {
		Long shopId=shopRoot.getCurrentShop().getId();	
		return this.service.getYearOrder(shopId);
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public OrderDTO saveOrUpdate(@RequestBody OrderDTO dto) {
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("orderItemService") IAdminService<Order, OrderDTO> baseService) {
		this.baseService = baseService;
	}

}
