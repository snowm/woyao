package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.OrderDTO;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryOrderItemRequestDTO;
import com.woyao.admin.service.IOrderItemAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.product.Product;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderItem;
import com.woyao.domain.purchase.OrderStatus;

@Service("orderItemService")
public class OrderItemServiceImpl extends AbstractAdminService<Order, OrderDTO> implements IOrderItemAdminService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public OrderDTO update(OrderDTO dto) {
		Order m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<OrderDTO> query(QueryOrderItemRequestDTO queryRequest) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("shopId", queryRequest.getShopId()));
		if (queryRequest.getStatusId() != null) {
			criterions.add(Restrictions.eq("status", OrderStatus.getEnum(queryRequest.getStatusId())));
		}
		if (queryRequest.getNicknameId() != null) {
			criterions.add(Restrictions.eq("consumer.id", queryRequest.getNicknameId()));
		}
		if (queryRequest.getMintotalFee() != null) {
			criterions.add(Restrictions.ge("totalFee", queryRequest.getMintotalFee()));
		}
		if (queryRequest.getMaxtotalFee() != null) {
			criterions.add(Restrictions.le("totalFee", queryRequest.getMaxtotalFee()));
		}
		if (queryRequest.getStartcreationDate() != null) {
			criterions.add(Restrictions.ge("modification.creationDate", queryRequest.getStartcreationDate()));
		}
		if (queryRequest.getEndcreationDate() != null) {
			criterions.add(Restrictions.le("modification.creationDate", queryRequest.getEndcreationDate()));
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
		List<OrderDTO> results = new ArrayList<>();
		for (Order m : ms) {
			OrderDTO dto = this.transferToFullDTO(m);
			dto.setTotalFee(m.getTotalFee() / 100);
			results.add(dto);
		}
		rs.setTotalCount(count);
		rs.setResults(results);
		return rs;
	}

	@Override
	public Order transferToDomain(OrderDTO dto) {
		Order m = new Order();
		BeanUtils.copyProperties(dto, m);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		return m;
	}

	/**
	 * 
	 * @param 商品转换
	 * @return
	 */
	public ProductDTO transferToSimpleDTO(Product m) {
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(m, dto);

		if (m.getShop() != null) {
			dto.setShopId(m.getShop().getId());
			dto.setShopName(m.getShop().getName());
		}
		dto.setTypeId(m.getType().getPersistedValue());
		if (m.getPic() != null) {
			dto.setMainPic(m.getPic().getUrl());
			dto.setMainPicId(m.getPic().getId());
		}
		dto.setUnitPrice(m.getUnitPrice() / 100);
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	/**
	 * 订单转换
	 */
	public OrderDTO transferToSimpleDTO(Order m) {
		OrderDTO dto = new OrderDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setTotalFee(m.getTotalFee() / 100);
		dto.setConsumerId(m.getConsumer().getId());
		dto.setConsumerName(m.getConsumer().getNickname());
		dto.setToProfileId(m.getToProfile().getId());
		dto.setToProfileName(m.getToProfile().getNickname());
		dto.setPrepayInfoId(m.getPrepayInfo().getId());
		dto.setStatusId(m.getStatus().getPersistedValue());
		if (m.getPrepayInfo() != null) {
			dto.setPrepayId(m.getPrepayInfo().getPrepayId());
			dto.setAppid(m.getPrepayInfo().getAppid());
			dto.setMchId(m.getPrepayInfo().getMchId());
			dto.setResultCode(m.getPrepayInfo().getResultCode());
			if (m.getPrepayInfo().getErrCode() != null) {
				dto.setErrCode(m.getPrepayInfo().getErrCode());
				dto.setErrCodeDes(m.getPrepayInfo().getErrCodeDes());
			}
			dto.setTradeType(m.getPrepayInfo().getTradeType());
			dto.setCodeUrl(m.getPrepayInfo().getCodeUrl());
		}
		dto.setSpbillCreateIp(m.getSpbillCreateIp());
		dto.setOrderNo(m.getOrderNo());
		if (m.getMsgId() != null) {
			dto.setMsgId(getMsg(m.getMsgId()).getId());
			dto.setMsgpic(getMsg(m.getMsgId()).getPicURL());
		}
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Transactional
	private ChatMsg getMsg(Long id) {
		return this.dao.get(ChatMsg.class, id);
	}

	@Override
	public OrderDTO transferToFullDTO(Order m) {

		return transferToSimpleDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public OrderDTO queryItem(QueryOrderItemRequestDTO request) {
		Long orderId = request.getOrderId();
		Order order = this.dao.get(Order.class, orderId);
		OrderDTO dto = transferToSimpleDTO(order);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		String hql = " from OrderItem as oi where oi.order.id= :orderId";
		List<OrderItem> OrderItems = this.dao.query(hql, paramMap);
		List<ProductDTO> prods = new ArrayList<>();
		for (OrderItem orderItem : OrderItems) {
			int num = orderItem.getQuantity();
			Long totalFee = (long) orderItem.getTotalFee() / 100;
			Product product = orderItem.getProduct();
			ProductDTO prodto = transferToSimpleDTO(product);
			prodto.setQuantity(num);
			prodto.setTotalFee(totalFee / 100);
			prods.add(prodto);
		}
		dto.setProducts(prods);
		return dto;
	}
	
}
