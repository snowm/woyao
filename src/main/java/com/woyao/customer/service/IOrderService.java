package com.woyao.customer.service;

import java.util.List;
import java.util.Map;

import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.dto.OrderItemDTO;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.purchase.OrderPrepayInfo;
import com.woyao.domain.purchase.OrderResultInfo;
import com.woyao.domain.purchase.OrderStatus;

public interface IOrderService {

	OrderDTO placeOrder(OrderDTO order);

	OrderDTO placeOrder(Long shopId, long consumerId, Long toProfileId, long productId, int quantity, String spbillCreateIp, Long msgId);

	OrderDTO placeOrder(Long shopId, ChatMsg chatMsg);

	OrderDTO placeOrder(Long shopId, long consumerId, Long toProfileId, List<OrderItemDTO> orderItemDTOs, String spbillCreateIp);

	OrderDTO placeOrder(Long shopId, long consumerId, Long toProfileId, List<OrderItemDTO> orderItemDTOs, String spbillCreateIp,
			Long msgId);

	OrderDTO get(long id);

	OrderDTO getFull(long id);

	OrderDTO getByOrderNo(String orderNo);

	void updateOrderStatus(long id, OrderStatus orderStatus, int version);

	void orderSuccess(long id, int version);

	void orderFail(long id, int version);

	List<Long> queryUnSubmittedOrders(int maxSize);

	void savePrepayInfo(OrderPrepayInfo info, long orderId);

	void savePayResultInfo(OrderResultInfo info, long orderId);

	Map<Long, Map<Long, Integer>> shopDailyMsgOrderConsumerReport();
	Map<Long, Map<Long, Integer>> shopWeekMsgOrderConsumerReport();
	Map<Long, Map<Long, Integer>> shopMonthMsgOrderConsumerReport();

}
