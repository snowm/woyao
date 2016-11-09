package com.woyao.admin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.snowm.utils.query.PaginationBean;
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryOrderRequestDTO;
import com.woyao.admin.dto.purchase.OrderDTO;
import com.woyao.admin.service.IOrderAdminService;
import com.woyao.admin.shop.dto.SMSParamsDTO;
import com.woyao.admin.shop.dto.ShopOrder;
import com.woyao.admin.shop.dto.ShopOrderDTO;
import com.woyao.customer.PeriodConfig;
import com.woyao.domain.Shop;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.product.MsgProduct;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductType;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderItem;
import com.woyao.domain.purchase.OrderStatus;

@Service("orderAdminService")
public class OrderAdminServiceImpl extends AbstractAdminService<Order, OrderDTO> implements IOrderAdminService {

	// private static final String HQL_SHOP_RPT = "from Order o where o.shopId =
	// :shopId and o.status = :status and o.msgId is not null"
	// + "and modification.creationDate > :startDt and modification.creationDate
	// <= :endDt";

	private static final String HQL_SHOP_RPT = "from OrderItem oi left outer join fetch oi.order o left outer join fetch oi.product p "
			+ "where o.shopId = :shopId and o.status = :status and o.msgId is not null "
			+ "and o.modification.creationDate > :startDt and o.modification.creationDate <= :endDt " + "and p.type = :type";

	private static final String sql = "select year(p.CREATION_DATE) yearOrder," + "month(p.CREATION_DATE) monthOrder,"
			+ "day(p.CREATION_DATE) dayOrder," + "sum(p.TOTAL_FEE) totalOrder " + "from PURCHASE_ORDER p where p.SHOP_ID =? "
			+ "and p.ORDER_STATUS=200 group by " + "year(p.CREATION_DATE), month(p.CREATION_DATE),day(p.CREATION_DATE)";

	@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<OrderDTO> query(QueryOrderRequestDTO queryRequest) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (queryRequest.getStatusId() != null) {
			criterions.add(Restrictions.eq("status", OrderStatus.getEnum(queryRequest.getStatusId())));
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
			dto.setTotalFee(m.getTotalFee() / 100);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public OrderDTO transferToSimpleDTO(Order m) {
		// 通过订单ID获取该订单下的所有产品
		String hql = "from OrderItem where order.id=" + m.getId();
		List<OrderItem> orderItems = dao.query(hql);
		OrderDTO dto = new OrderDTO();
		BeanUtils.copyProperties(m, dto);
		List<ProductDTO> pdtos = new ArrayList<>();
		for (OrderItem orderItem : orderItems) {
			int quantity = orderItem.getQuantity();
			long totalFee = orderItem.getTotalFee();
			Product product = this.dao.get(Product.class, orderItem.getProduct().getId());
			ProductDTO pdto = new ProductDTO();
			BeanUtils.copyProperties(product, pdto);
			pdto.setQuantity(quantity);
			pdto.setTotalFee(totalFee);
			pdto.setTypeId(product.getType().getPersistedValue());
			if (product.getShop() != null) {
				pdto.setShopId(product.getShop().getId());
				pdto.setShopName(product.getShop().getName());
			}
			pdtos.add(pdto);
		}
		dto.setProducts(pdtos);
		dto.setConsumerId(m.getConsumer().getId());
		dto.setConsumerName(m.getConsumer().getNickname());
		dto.setToProfileId(m.getToProfile().getId());
		dto.setToProfileName(m.getToProfile().getNickname());
		dto.setStatusId(m.getStatus().getPersistedValue());
		if (m.getPrepayInfo() != null) {
			dto.setPrepayInfoId(m.getPrepayInfo().getId());
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
		Integer year = cb.get(Calendar.YEAR);// 获取年
		Integer month = cb.get(Calendar.MONTH) + 1;// 获取月
		Integer day = cb.get(Calendar.DATE);// 获取日
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
	public SMSParamsDTO getShopDailyReport(Shop shop) {
		SMSParamsDTO dto = new SMSParamsDTO();
		dto.setDate(new Date());
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("shopId", shop.getId());
		paramMap.put("status", OrderStatus.SUCCESS);
		Calendar thisDay = Calendar.getInstance();
		thisDay.set(Calendar.HOUR_OF_DAY, 9);
		paramMap.put("startDt", PeriodConfig.getDailyStartDt(thisDay));
		paramMap.put("endDt", PeriodConfig.getDailyEndDt(thisDay));
		paramMap.put("type", ProductType.MSG);
		List<OrderItem> lists = this.dao.query(HQL_SHOP_RPT, paramMap);

		int bapinCount = 0;
		int bapinTotal = 0;

		int liwuCount = 0;
		int liwuTotal = 0;
		for (OrderItem e : lists) {
			MsgProduct prod = (MsgProduct) e.getProduct();
			if (StringUtils.isBlank(prod.getEffectCode())) {
				bapinCount++;
				bapinTotal += e.getOrder().getTotalFee();
			} else {
				liwuCount++;
				liwuTotal += e.getOrder().getTotalFee();
			}
		}

		String shopName = shop.getName();
		String shopPhone = shop.getMobiles();
		dto.setBaNum(bapinCount);
		dto.setBaTotal((float) bapinTotal / 100);
		dto.setLiNum(liwuCount);
		dto.setLiTotal((float) liwuTotal / 100);
		dto.setTotal((float) (bapinTotal + liwuTotal) / 100);
		dto.setName(shopName);
		dto.setPhone(shopPhone);
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<SMSParamsDTO> listShopDailyReports(PaginationQueryRequestDTO request) {
		List<Criterion> criterions = new ArrayList<>();
		criterions.add(Restrictions.eq("logicalDelete.deleted", false));

		PaginationBean<SMSParamsDTO> pb = new PaginationBean<>();
		List<SMSParamsDTO> dtos = new ArrayList<>();
		pb.setPageNumber(request.getPageNumber());
		pb.setPageSize(request.getPageSize());
		pb.setResults(dtos);
		pb.setTotalCount(0L);

		List<Shop> results = this.dao.query(Shop.class, criterions, request.getPageNumber(), request.getPageSize());
		if (CollectionUtils.isEmpty(results)) {
			return pb;
		}
		results.forEach(e -> {
			SMSParamsDTO dto = this.getShopDailyReport(e);
			dtos.add(dto);
		});
		pb.setTotalCount(dtos.size());
		return pb;
	}

	private ChatMsg getMsg(Long id) {
		return this.dao.get(ChatMsg.class, id);
	}

	@Override
	public Order transferToDomain(OrderDTO dto) {
		Order m = new Order();
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
		return null;
	}

	@Transactional
	@Override
	public Order get(Long OrderId) {
		return this.dao.get(Order.class, OrderId);
	}

}
