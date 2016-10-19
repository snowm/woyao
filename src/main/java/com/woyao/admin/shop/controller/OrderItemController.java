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
import com.woyao.admin.dto.product.OrderItemDTO;
import com.woyao.admin.dto.product.QueryOrderItemRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IOrderItemAdminService;
import com.woyao.admin.shop.ShopRootController;
import com.woyao.domain.purchase.OrderItem;

@Controller
@RequestMapping(value = "/shop/admin/order")
public class OrderItemController extends AbstractBaseController<OrderItem, OrderItemDTO> {

	@Resource(name = "orderItemService")
	private IOrderItemAdminService service;
	
	@Autowired
	private ShopRoot shopRoot;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<OrderItemDTO> query(QueryOrderItemRequestDTO request) {
		System.out.println(shopRoot.getCurrentShop().getId()+"？？？？？？？？？？？？？？？？？？？？？");
		Long shopId=shopRoot.getCurrentShop().getId();
		request.setShopId(shopId);
		PaginationBean<OrderItemDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public OrderItemDTO saveOrUpdate(@RequestBody OrderItemDTO dto) {
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("orderItemService") IAdminService<OrderItem, OrderItemDTO> baseService) {
		this.baseService = baseService;
	}

}
