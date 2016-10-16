package com.woyao.customer.queue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.woyao.customer.DefaultOrderProcessor;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.service.IOrderService;

@Component("submitOrderMessageConsumer")
public class SubmitOrderMessageConsumer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "defaultOrderProcessor")
	private DefaultOrderProcessor orderProcessor;

	public boolean consume(long orderId) {
		try {
			OrderDTO dto = this.orderService.getFull(orderId);
			orderProcessor.process(dto);
			return true;
		} catch (Exception e) {
			logger.error("consume order :" + orderId + " exception occurred!", e);
		}
		return false;
	}
}
