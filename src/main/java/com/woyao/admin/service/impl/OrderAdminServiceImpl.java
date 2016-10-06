package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.OrderDTO;
import com.woyao.admin.dto.product.QueryOrderRequestDTO;
import com.woyao.admin.service.IOrderAdminService;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderItem;

@Service("orderAdminService")
public class OrderAdminServiceImpl extends AbstractAdminService<Order, OrderDTO> implements IOrderAdminService {
	

	@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<OrderDTO> query(QueryOrderRequestDTO queryRequest) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if(queryRequest.getStatusId()!=null){
			criterions.add(Restrictions.eq("statusId", queryRequest.getStatusId()));
		}
		if(queryRequest.getMintotalFee()!=null){
			criterions.add(Restrictions.ge("totalFee", queryRequest.getMintotalFee()));
		}
		if(queryRequest.getMaxtotalFee()!=null){
			criterions.add(Restrictions.gt("totalFee", queryRequest.getMaxtotalFee()));
		}
		if(queryRequest.getProductId()!=null){
			criterions.add(Restrictions.eq("productId", queryRequest.getProductId()));
		}
		if(queryRequest.getProductType()!=null){
			criterions.add(Restrictions.eq("productType", queryRequest.getProductType()));
		}
		if(queryRequest.getShopId()!=null){
			criterions.add(Restrictions.eq("shopId", queryRequest.getShopId()));
		}
		if(queryRequest.getStartcreationDate()!=null){
			criterions.add(Restrictions.gt("creationDate", queryRequest.getStartcreationDate()));
		}
		if(queryRequest.getEndcreationDate()!=null){
			criterions.add(Restrictions.gt("creationDate", queryRequest.getEndcreationDate()));
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
	public OrderDTO transferToSimpleDTO(Order m) {
		OrderDTO dto = new OrderDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setConsumerId(m.getConsumer().getId());
		dto.setConsumerName(m.getConsumer().getNickname());
		dto.setToProfileId(m.getToProfile().getId());
		dto.setToProfileName(m.getToProfile().getNickname());
		dto.setPrepayInfoId(m.getPrepayInfo().getId());
		dto.setStatusId(m.getStatus().getPersistedValue());
		//订单下的产品
		dto.setProductId(this.dao.get(OrderItem.class, m.getId()).getProduct().getId());
		dto.setProductName(this.dao.get(OrderItem.class, m.getId()).getProduct().getName());
		dto.setProductType(this.dao.get(OrderItem.class, m.getId()).getProduct().getType().getPersistedValue());
		dto.setProductdescription(this.dao.get(OrderItem.class, m.getId()).getProduct().getDescription());
		dto.setProductunitPrice(this.dao.get(OrderItem.class, m.getId()).getUnitPrice());
		dto.setProductquantity(this.dao.get(OrderItem.class, m.getId()).getQuantity());
		dto.setTotalFee(Integer.valueOf((int)this.dao.get(OrderItem.class, m.getId()).getTotalFee()));
		//通过订单获取商品，在通过商品获取商店
		dto.setShopId(this.dao.get(OrderItem.class, m.getId()).getProduct().getShop().getId());
		dto.setShopName(this.dao.get(OrderItem.class, m.getId()).getProduct().getShop().getName());
		
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public Order transferToDomain(OrderDTO dto) {
		Order m=new Order();
		BeanUtils.copyProperties(m, dto);	
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		return m;
	}

	@Override
	public OrderDTO transferToFullDTO(Order m) {	
		return transferToSimpleDTO(m);
	}

	@Override
	public OrderDTO update(OrderDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}


}
