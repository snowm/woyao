package com.woyao.customer.service;

import java.util.List;

import com.woyao.customer.dto.OrderDTO;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.purchase.OrderPrepayInfo;
import com.woyao.domain.purchase.OrderResultInfo;
import com.woyao.domain.purchase.OrderStatus;

public interface IOrderService {

	OrderDTO placeOrder(OrderDTO order);

	OrderDTO placeOrder(long consumerId, long productId, int quantity, String spbillCreateIp, Long msgId);

	OrderDTO placeOrder(ChatMsg chatMsg);

	OrderDTO get(long id);

	OrderDTO getFull(long id);
	
	OrderDTO getByOrderNo(String orderNo);
	
	void updateOrderStatus(long id, OrderStatus orderStatus);

	List<Long> queryUnSubmittedOrders(int maxSize);
	
	void savePrepayInfo(OrderPrepayInfo info, long orderId);

	void savePayResultInfo(OrderResultInfo info, long orderId);
	
}
