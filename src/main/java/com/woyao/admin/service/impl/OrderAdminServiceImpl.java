package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.domain.purchase.Order;
import com.woyao.admin.dto.purchase.OrderDTO;
import com.woyao.admin.dto.purchase.QueryOrdersRequestDTO;
import com.woyao.admin.service.IOrderAdminService;

@Service("orderAdminService")
public class OrderAdminServiceImpl extends AbstractAdminService<Order, OrderDTO> implements IOrderAdminService {
	
	@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<OrderDTO> query(QueryOrdersRequestDTO queryRequest) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!StringUtils.isEmpty(queryRequest.getTbOrderNo())) {
			criterions.add(Restrictions.eq("tbOrderNo", queryRequest.getTbOrderNo()));
		}
		List<org.hibernate.criterion.Order> orders = new ArrayList<>();
		orders.add(org.hibernate.criterion.Order.desc("logicalDelete.enabled"));
		orders.add(org.hibernate.criterion.Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<Order> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, queryRequest.getPageNumber(), queryRequest.getPageSize());
		}

		PaginationBean<OrderDTO> rs = new PaginationBean<>(queryRequest.getPageNumber(), queryRequest.getPageSize());
		rs.setTotalCount(count);
		List<OrderDTO> results = new ArrayList<>();
		for (Order m : ms) {
			OrderDTO dto = this.transferToDTO(m, true);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public OrderDTO update(OrderDTO dto) {
		return dto;
	}

	@Override
	public OrderDTO transferToSimpleDTO(Order m) {
		OrderDTO dto = new OrderDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public Order transferToDomain(OrderDTO dto) {
		return null;
	}

	@Override
	public OrderDTO transferToFullDTO(Order m) {
		return null;
	}

}
