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
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryOrderRequestDTO;
import com.woyao.admin.service.IOrderAdminService;
import com.woyao.domain.product.Product;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderItem;
import com.woyao.domain.purchase.OrderStatus;

@Service("orderAdminService")
public class OrderAdminServiceImpl extends AbstractAdminService<Order, OrderDTO> implements IOrderAdminService {
	

	@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<OrderDTO> query(QueryOrderRequestDTO queryRequest) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(queryRequest.getStatusId()!=null){
			criterions.add(Restrictions.eq("status", OrderStatus.getEnum(queryRequest.getStatusId())));
		}
		if(queryRequest.getMintotalFee()!=null){
			criterions.add(Restrictions.ge("totalFee", queryRequest.getMintotalFee()));
		}
		if(queryRequest.getMaxtotalFee()!=null){
			criterions.add(Restrictions.le("totalFee", queryRequest.getMaxtotalFee()));
		}
		if(queryRequest.getStartcreationDate()!=null){
			criterions.add(Restrictions.ge("modification.creationDate", queryRequest.getStartcreationDate()));
		}
		if(queryRequest.getEndcreationDate()!=null){
			criterions.add(Restrictions.le("modification.creationDate", queryRequest.getEndcreationDate()));
		}
		if (queryRequest.getDeleted() != null) {
			criterions.add(Restrictions.eq("logicalDelete.deleted", queryRequest.getDeleted()));
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
		//通过订单ID获取该订单下的所有产品
		String hql="from OrderItem where order.id="+m.getId();
		List<OrderItem> orderItems=dao.query(hql);
		OrderDTO dto=new OrderDTO();
		BeanUtils.copyProperties(m,dto);
		List<ProductDTO> pdtos=new ArrayList<>();;
		for (OrderItem orderItem : orderItems) {
			System.out.println(orderItem.getId());
			hql="from Product where id="+orderItem.getProduct().getId();
			Product product=this.dao.queryUnique(hql);
			ProductDTO pdto=new ProductDTO();
			BeanUtils.copyProperties(product,pdto);
			pdto.setTypeId(product.getType().getPersistedValue());
			pdto.setShopId(product.getShop().getId());
			pdto.setShopName(product.getShop().getName());
			pdtos.add(pdto);
			
		} 
		dto.setProducts(pdtos);	
		dto.setConsumerId(m.getConsumer().getId());
		dto.setConsumerName(m.getConsumer().getNickname());
		dto.setToProfileId(m.getToProfile().getId());
		dto.setToProfileName(m.getToProfile().getNickname());
		dto.setPrepayInfoId(m.getPrepayInfo().getId());
		dto.setStatusId(m.getStatus().getPersistedValue());
		
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
