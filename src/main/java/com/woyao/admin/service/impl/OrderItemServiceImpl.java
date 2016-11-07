package com.woyao.admin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
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
import com.woyao.admin.shop.dto.SMSParamsDTO;
import com.woyao.admin.shop.dto.ShopOrder;
import com.woyao.admin.shop.dto.ShopOrderDTO;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.product.MsgProduct;
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

	/**
	 * 
	 * @param shopId
	 * @return 年月日的统计金额数
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public ShopOrderDTO getYearOrder(Long shopId) {
		Session session = this.dao.getSessionFactory().getCurrentSession();
		Calendar cb = Calendar.getInstance();// 获取系统当前时间
		Integer year = cb.get(cb.YEAR);// 获取年
		Integer month = cb.get(cb.MONTH) + 1;// 获取月
		Integer day = cb.get(cb.DATE);// 获取日
		String sql = "select year(p.CREATION_DATE) yearOrder," + "month(p.CREATION_DATE) monthOrder," + "day(p.CREATION_DATE) dayOrder,"
				+ "sum(p.TOTAL_FEE) totalOrder " + "from purchase_order p where p.SHOP_ID =? " + "and p.ORDER_STATUS=200 group by "
				+ "year(p.CREATION_DATE), month(p.CREATION_DATE),day(p.CREATION_DATE)";
		List<ShopOrder> lists = session.createSQLQuery(sql).setLong(0, shopId)
				.setResultTransformer(Transformers.aliasToBean(ShopOrder.class)).list();
		ShopOrderDTO dto = new ShopOrderDTO();
		int ytotle = 0;// 年总金额
		int mtotle = 0;// 月总金额
		int dtotle = 0;// 日总金额
		if (lists.isEmpty() || lists.size() == 0) {
			return null;
		}
		for (ShopOrder shopOrder : lists) {
			if (year.equals(shopOrder.getYearOrder())) {
				ytotle += shopOrder.getTotalOrder().intValue();
			}
			if (month.equals(shopOrder.getMonthOrder()) && year.equals(shopOrder.getYearOrder())) {
				mtotle += shopOrder.getTotalOrder().intValue();
			}
			if (day.equals(shopOrder.getDayOrder()) && month.equals(shopOrder.getMonthOrder()) && year.equals(shopOrder.getYearOrder())) {
				dtotle += shopOrder.getTotalOrder().intValue();
			}
		}
		dto.setYearOrder(year);
		dto.setMonthOrder(month);
		dto.setDayOrder(day);
		dto.setYearTotal(ytotle / 100);
		dto.setMonthTotal(mtotle / 100);
		dto.setDayTotal(dtotle / 100);
		List<ShopOrder> ShopOrders = dto.getShopOrders();
		for (int i = 0; i < 30; i++) {
			if (i == lists.size()) {
				break;
			}
			ShopOrder s = lists.get(i);
			lists.get(i).setTotalOrder(new BigDecimal(lists.get(i).getTotalOrder().intValue() / 100));
			ShopOrders.add(s);
		}
		dto.setShopOrders(ShopOrders);
		return dto;
	}

	/**
	 * 商家订单报表
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public SMSParamsDTO queryReport(Long shopId) {
		SMSParamsDTO dto = new SMSParamsDTO();
		dto.setDate(new Date());	
		String hql = "from Order o where o.shopId =:shopId and o.status =:status and modification.creationDate >=:startdate and modification.creationDate <=:edate";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("shopId", shopId);
		paramMap.put("status", OrderStatus.getEnum(200));
		paramMap.put("startdate", dateFormat(1));
		paramMap.put("edate", dateFormat(0));
		List<Order> lists = this.dao.query(hql, paramMap);
		int baTotal = 0;
		int liTotal = 0;
		int baCount = 0;
		int liCount = 0;
		for (Order order : lists) {
			Long orderId = order.getId();
			hql = "select oi.product from OrderItem oi where oi.order.id=" + orderId;
			List<MsgProduct> msgProducts = this.dao.query(hql);
			for (MsgProduct msgProduct : msgProducts) {
				System.out.println(msgProduct);
				if (!msgProduct.getEffectCode().isEmpty()) {
					liTotal += msgProduct.getUnitPrice();
					liCount++;
				}else{
					baTotal += msgProduct.getUnitPrice();
					baCount++;
				}
			}
		}
		Shop shop = this.dao.get(Shop.class, shopId);
		String shopName=shop.getName();
		String shopPhone=shop.getMobiles();
		dto.setBaTotal(baTotal);
		dto.setLiTotal(liTotal);
		dto.setBaNum(baCount);
		dto.setLiNum(liCount);
		dto.setTotal(baTotal + liTotal);
		dto.setName(shopName);
		dto.setPhone(shopPhone);
		return dto;
	}
	/**
	 * @param 传入需要减多少天
	 * @return
	 */
	private Date dateFormat(int index){
		Calendar calendar = Calendar.getInstance();
		Integer year = calendar.get(calendar.YEAR);// 获取年
		Integer month = calendar.get(calendar.MONTH);// 获取月
		Integer day = calendar.get(calendar.DATE);// 获取日
		if(index==1){
			calendar.set(year, month, day-index, 20, 0, 0); 		
			return calendar.getTime();
		}else{
			calendar.set(year, month, day-index, 8, 0, 0); 		
			return calendar.getTime();
		}
	}
	/**
	 * 查询所有商店
	 */
	public List<Shop> getShop(){
		return this.dao.query("from Shop");
	}
}
