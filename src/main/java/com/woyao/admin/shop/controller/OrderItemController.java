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
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.product.OrderDTO;
import com.woyao.admin.dto.product.QueryOrderItemRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IOrderAdminService;
import com.woyao.admin.service.IOrderItemAdminService;
import com.woyao.admin.shop.dto.SMSParamsDTO;
import com.woyao.admin.shop.dto.ShopOrderDTO;
import com.woyao.domain.purchase.Order;

@Controller
@RequestMapping(value = "/shop/admin/order")
public class OrderItemController extends AbstractBaseController<Order, OrderDTO> {

	@Resource(name = "orderItemService")
	private IOrderItemAdminService service;

	@Resource(name = "orderAdminService")
	private IOrderAdminService orderService;

	@Autowired
	private ShopRoot shopRoot;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<OrderDTO> query(QueryOrderItemRequestDTO request) {
		Long shopId = shopRoot.getCurrentShop().getId();
		request.setShopId(shopId);
		PaginationBean<OrderDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "/detil" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody 
	public OrderDTO queryitem(QueryOrderItemRequestDTO request) {
		Long shopId = shopRoot.getCurrentShop().getId();
		request.setShopId(shopId);
		OrderDTO result = this.service.queryItem(request);
		return result;
	}

	@RequestMapping(value = { "/test" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<SMSParamsDTO> querytest() {
		PaginationQueryRequestDTO request = new PaginationQueryRequestDTO();
		request.setPageNumber(1L);
		request.setPageSize(20);
		return this.orderService.listShopDailyReports(request);
	}

	@RequestMapping(value = { "/main" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopOrderDTO queryItem() {
		Long shopId = shopRoot.getCurrentShop().getId();
		return this.orderService.getYearOrder(shopId);
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
