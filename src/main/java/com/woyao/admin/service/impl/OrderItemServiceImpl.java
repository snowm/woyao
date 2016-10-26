package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
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
import com.woyao.admin.shop.dto.ShopOrder;
import com.woyao.admin.shop.dto.ShopOrderDTO;
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
		Map<String,Object> paramMap=new HashMap<String,Object>();
		Long shopId=queryRequest.getShopId();	
		paramMap.put("shopId", shopId);
		StringBuffer sb=new StringBuffer("select distinct oi.order from OrderItem as oi where oi.product.shop.id= :shopId ");
		if(queryRequest.getMintotalFee()!=null){
			paramMap.put("mintotalFee", queryRequest.getMintotalFee());
			sb.append(" and oi.order.totalFee >= :mintotalFee");
		}
		if(queryRequest.getMaxtotalFee()!=null){
			paramMap.put("maxtotalFee", queryRequest.getMaxtotalFee());
			sb.append(" and oi.order.totalFee <= :maxtotalFee");			
		}
		if(queryRequest.getStartcreationDate()!=null){
			paramMap.put("startcreationDate", queryRequest.getStartcreationDate());
			sb.append(" and oi.order.modification.creationDate >= :startcreationDate");	
		}
		if(queryRequest.getStatusId()!=null){
			paramMap.put("status", OrderStatus.getEnum(queryRequest.getStatusId()));
			sb.append(" and oi.order.status= :status");	
		}
		if(queryRequest.getNicknameId()!=null){
			paramMap.put("nicknameId", queryRequest.getNicknameId());
			sb.append(" and oi.order.consumer.id= :nicknameId");	
		}
		if(queryRequest.getEndcreationDate()!=null){
			paramMap.put("endcreationDate", queryRequest.getEndcreationDate());
			sb.append(" and oi.order.modification.creationDate <= :endcreationDate");	
		}
		String hql=sb.toString();
		Integer count=this.dao.query(hql, paramMap).size();
		List<Order> ms =new ArrayList<>();
		if(count!=null || count!=0){
			ms=this.dao.query(hql, paramMap, queryRequest.getPageNumber(), queryRequest.getPageSize());
		}
		PaginationBean<OrderDTO> rs = new PaginationBean<>(queryRequest.getPageNumber(), queryRequest.getPageSize());
		List<OrderDTO> results = new ArrayList<>();
		for (Order m : ms) {			
			OrderDTO dto = this.transferToFullDTO(m);
			results.add(dto);
		}	
		rs.setTotalCount(count);	
		rs.setResults(results);
		return rs;
	}

	@Override
	public Order transferToDomain(OrderDTO dto) {
		Order m=new Order();
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
		ProductDTO dto=new ProductDTO();
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
		OrderDTO dto=new OrderDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setConsumerId(m.getConsumer().getId());
		dto.setConsumerName(m.getConsumer().getNickname());
		dto.setToProfileId(m.getToProfile().getId());
		dto.setToProfileName(m.getToProfile().getNickname());
		dto.setPrepayInfoId(m.getPrepayInfo().getId());
		dto.setStatusId(m.getStatus().getPersistedValue());
		if(m.getPrepayInfo()!=null){		
			dto.setPrepayId(m.getPrepayInfo().getPrepayId());
			dto.setAppid(m.getPrepayInfo().getAppid());
			dto.setMchId(m.getPrepayInfo().getMchId());
			dto.setResultCode(m.getPrepayInfo().getResultCode());
			if(m.getPrepayInfo().getErrCode()!=null){
				dto.setErrCode(m.getPrepayInfo().getErrCode());
				dto.setErrCodeDes(m.getPrepayInfo().getErrCodeDes());
			}
			dto.setTradeType(m.getPrepayInfo().getTradeType());
			dto.setCodeUrl(m.getPrepayInfo().getCodeUrl());			
		}
		dto.setSpbillCreateIp(m.getSpbillCreateIp());
		dto.setOrderNo(m.getOrderNo());
		if(m.getMsgId()!=null){		
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
	private ChatMsg getMsg(Long id){
		return this.dao.get(ChatMsg.class, id);
	}
	@Override
	public OrderDTO transferToFullDTO(Order m) {
		
		return transferToSimpleDTO(m);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public OrderDTO queryItem(QueryOrderItemRequestDTO request) {
		Long orderId=request.getOrderId();	
		Order order=this.dao.get(Order.class, orderId);
		OrderDTO dto=transferToSimpleDTO(order);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("orderId", orderId);
		String hql=" from OrderItem as oi where oi.order.id= :orderId";
		List<OrderItem> OrderItems=this.dao.query(hql,paramMap);
		List<ProductDTO> prods=new ArrayList<>();
		for (OrderItem orderItem : OrderItems) {
			int num = orderItem.getQuantity();
			Long totalFee = (long) orderItem.getTotalFee();
			Product product = orderItem.getProduct();
			ProductDTO prodto=transferToSimpleDTO(product);
			prodto.setQuantity(num);
			prodto.setTotalFee(totalFee);
			prods.add(prodto);
		}
		dto.setProducts(prods);
		return dto;
	}

	/**
	 * 
	 * @param shopId
	 * @return 年月日的统计金额数
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)	
	public ShopOrderDTO getYearOrder(Long shopId){
		Session session=this.dao.getSessionFactory().openSession();
		Calendar cb=Calendar.getInstance();//获取系统当前时间
		Integer year=cb.get(cb.YEAR);//获取年
		Integer month=cb.get(cb.MONTH)+1;//获取月
		Integer day=cb.get(cb.DATE);//获取日
		String sql="select year(p.CREATION_DATE) yearOrder,month(p.CREATION_DATE) monthOrder,"
				+ "day(p.CREATION_DATE) dayOrder,"
				+ "sum(p.TOTAL_FEE) totalOrder "
				+ "from purchase_order p where p.id in "
				+ "(select DISTINCT t.ORDER_ID from order_item t where t.PRODUCT_ID "
				+ "in(select id from product where shop_id=?)"
				+ ") and year(p.CREATION_DATE)=? and p.ORDER_STATUS!=400 group by year(p.CREATION_DATE),month(p.CREATION_DATE),day(p.CREATION_DATE)";
		List<ShopOrder> lists=session.createSQLQuery(sql).setLong(0, shopId).setInteger(1, year).setResultTransformer(Transformers.aliasToBean(ShopOrder.class)).list();
		ShopOrderDTO dto=new ShopOrderDTO();
		int ytotle=0;//年总金额
		int mtotle=0;//月总金额
		int dtotle=0;//日总金额
		for (ShopOrder shopOrder : lists) {
			if(year.equals(shopOrder.getYearOrder())){				
				ytotle+=shopOrder.getTotalOrder().intValue();
			}
			if(month.equals(shopOrder.getMonthOrder())){
				mtotle+=Integer.parseInt(shopOrder.getTotalOrder().toString());
			}
			if(day.equals(shopOrder.getDayOrder())){
				dtotle+=Integer.parseInt(shopOrder.getTotalOrder().toString());
			}
		}
		dto.setYearOrder(year);
		dto.setMonthOrder(month);
		dto.setDayOrder(day);
		dto.setYearTotal(ytotle);
		dto.setMonthTotal(mtotle);
		dto.setDayTotal(dtotle);
		dto.setShopOrders(lists);
		return dto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
