package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryOrderRequestDTO;
import com.woyao.admin.dto.purchase.OrderDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IOrderAdminService;
import com.woyao.domain.purchase.Order;

@Controller
@RequestMapping(value = "/admin/order")
public class OrderAdminController extends AbstractBaseController<Order, OrderDTO> {

	@Resource(name = "orderAdminService")
	private IOrderAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<OrderDTO> query(QueryOrderRequestDTO request) {
		PaginationBean<OrderDTO> result = this.service.query(request);
		return result;
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
	public void setBaseService(@Qualifier("orderAdminService") IAdminService<Order, OrderDTO> baseService) {
		this.baseService = baseService;
	}

}
