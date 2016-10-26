package com.woyao.customer.service.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.dto.OrderItemDTO;
import com.woyao.customer.service.IOrderService;
import com.woyao.customer.translator.DefaultTranslator;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.product.Product;
import com.woyao.domain.profile.ProfileWX;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderItem;
import com.woyao.domain.purchase.OrderPrepayInfo;
import com.woyao.domain.purchase.OrderResultInfo;
import com.woyao.domain.purchase.OrderStatus;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
	
	public static final String DT_PATTERN = "yyyyMMddHHmmssSSS";
	public static final DateFormat DF = new SimpleDateFormat(DT_PATTERN);
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Random r = new Random();

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	@Override
	public OrderDTO placeOrder(OrderDTO order) {
		return null;
	}

	@Transactional
	@Override
	public OrderDTO placeOrder(long consumerId, Long toProfileId, long productId, int quantity, String spbillCreateIp, Long msgId) {
		OrderItemDTO oiDTO = new OrderItemDTO();
		oiDTO.setProductId(productId);
		oiDTO.setQuantity(quantity);
		
		List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
		orderItemDTOs.add(oiDTO);
		
		OrderDTO orderDTO = this.placeOrder(consumerId, toProfileId, orderItemDTOs, spbillCreateIp, msgId);
		return orderDTO;
	}

	@Transactional
	@Override
	public OrderDTO placeOrder(long consumerId, Long toProfileId, List<OrderItemDTO> orderItemDTOs, String spbillCreateIp) {
		OrderDTO orderDTO = this.placeOrder(consumerId, toProfileId, orderItemDTOs, spbillCreateIp, null);
		return orderDTO;
	}

	@Transactional
	@Override
	public OrderDTO placeOrder(long consumerId, Long toProfileId, List<OrderItemDTO> orderItemDTOs, String spbillCreateIp, Long msgId) {
		if(CollectionUtils.isEmpty(orderItemDTOs)){
			throw new IllegalArgumentException("订单是空的！");
		}
		ProfileWX consumer = this.commonDao.get(ProfileWX.class, consumerId);
		if (consumer == null) {
			throw new IllegalArgumentException("消费用户不存在！");
		}
		ProfileWX toProfile = consumer;
		if (toProfileId != null) {
			toProfile = this.commonDao.get(ProfileWX.class, toProfileId);
			if (toProfile == null) {
				throw new IllegalArgumentException("对方用户不存在！");
			}
		}

		Order order = new Order();
		List<OrderItem> ois = new ArrayList<>();
		int totalFee = 0;
		for (OrderItemDTO oiDTO : orderItemDTOs) {
			OrderItem oi = DefaultTranslator.translateToDomain(oiDTO);;
			Product product = this.commonDao.get(Product.class, oi.getProduct().getId());
			oi.setUnitPrice(product.getUnitPrice());
			oi.calcTotalFee();
			oi.setOrder(order);
			ois.add(oi);
			totalFee += oi.getTotalFee();
		}
		
		String orderNo = DF.format(new Date()) + r.nextInt(1000);
		order.setOrderNo(orderNo);
		order.setConsumer(consumer);
		order.setSpbillCreateIp(spbillCreateIp);
		order.setToProfile(toProfile);
		order.setTotalFee(totalFee);
		order.setStatus(OrderStatus.SAVED);
		if (msgId != null) {
			order.setMsgId(msgId);
		}
		this.commonDao.save(order);

		List<OrderItemDTO> oiDTOrs = new ArrayList<>();
		for (OrderItem oi : ois) {
			this.commonDao.save(oi);
			oiDTOrs.add(DefaultTranslator.translateToDTO(oi));
		}

		if (msgId != null) {
			this.relateMsgOrder(order.getId(), msgId);
		}

		OrderDTO orderDTO = DefaultTranslator.translateToDTO(order);
		orderDTO.setItems(oiDTOrs);

		return orderDTO;
	}

	private void relateMsgOrder(long orderId, long msgId) {
		ChatMsg m = this.commonDao.get(ChatMsg.class, msgId);
		m.setFree(false);
		m.setPayed(false);
		m.setOrderId(orderId);
		this.commonDao.update(m);
	}
	
	@Override
	public OrderDTO placeOrder(ChatMsg chatMsg) {
		return this.placeOrder(chatMsg.getFrom(), chatMsg.getTo(), chatMsg.getProductId(), 1, chatMsg.getRemoteAddr(), chatMsg.getId());
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	@Override
	public OrderDTO get(long id) {
		Order order = this.commonDao.get(Order.class, id);
		if (order == null) {
			return null;
		}
		OrderDTO dto = DefaultTranslator.translateToDTO(order);
		return dto;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	@Override
	public OrderDTO getFull(long id) {
		Order order = this.commonDao.get(Order.class, id);
		if (order == null) {
			return null;
		}

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", id);
		List<OrderItem> items = this.commonDao.query("from OrderItem where order.id = :orderId", paramMap);
		OrderDTO dto = this.generateOrderDTO(order, items);
		return dto;
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	@Override
	public OrderDTO getByOrderNo(String orderNo) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderNo", orderNo);
		Order order = this.commonDao.queryUnique("from Order where orderNo = :orderNo", paramMap);
		OrderDTO dto = DefaultTranslator.translateToDTO(order);
		return dto;
	}

	@Transactional
	@Override
	public void updateOrderStatus(long id, OrderStatus status, int version) {
		Order order = this.commonDao.get(Order.class, id);
		order.setVersion(version);
		order.setStatus(status);
	}

	private static final String SQL_UN_SUBMITTED_ORDERS = "SELECT lo.id FROM PURCHASE_ORDER lo "
			+ "	WHERE lo.ORDER_STATUS = :orderStatus AND lo.ENABLED = 1 " + " AND lo.DELETED = 0 ORDER BY lo.id ASC LIMIT 0, :size";

	private Integer unSubmittedStatus = OrderStatus.SAVED.getPersistedValue();

	@Transactional
	@Override
	public List<Long> queryUnSubmittedOrders(int maxSize) {
		Session session = this.commonDao.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(SQL_UN_SUBMITTED_ORDERS);
		query.setParameter("orderStatus", unSubmittedStatus);
		query.setParameter("size", maxSize);
		return toLongList(query);
	}

	@Transactional
	@Override
	public void savePrepayInfo(OrderPrepayInfo info, long orderId) {
		this.commonDao.save(info);
		Order order = this.commonDao.get(Order.class, orderId);
		order.setPrepayInfo(info);
	}

	@Transactional
	@Override
	public void savePayResultInfo(OrderResultInfo info, long orderId) {
		this.commonDao.save(info);
		Order order = this.commonDao.get(Order.class, orderId);
		order.setResultInfo(info);
	}

	private OrderDTO generateOrderDTO(Order order, List<OrderItem> items) {
		OrderDTO orderDTO = DefaultTranslator.translateToDTO(order);
		List<OrderItemDTO> ois = new ArrayList<>();
		if (!CollectionUtils.isEmpty(items)) {
			for (OrderItem oi : items) {
				OrderItemDTO oiDTO = DefaultTranslator.translateToDTO(oi);
				ois.add(oiDTO);
			}
		}
		orderDTO.setItems(ois);
		return orderDTO;
	}

	@SuppressWarnings("unchecked")
	private List<Long> toLongList(SQLQuery query) {
		List<BigInteger> result = query.list();
		List<Long> t = new ArrayList<>();
		if (result == null || result.isEmpty()) {
			return t;
		}
		for (BigInteger e : result) {
			if (e == null) {
				continue;
			}
			t.add(e.longValue());
		}
		return t;
	}


}
