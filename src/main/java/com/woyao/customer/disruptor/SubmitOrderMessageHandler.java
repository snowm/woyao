package com.woyao.customer.disruptor;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woyao.customer.DefaultOrderProcessor;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.service.IOrderService;
import com.woyao.domain.purchase.OrderStatus;

public class SubmitOrderMessageHandler extends AbstractEventHandler<LongEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "defaultOrderProcessor")
	private DefaultOrderProcessor orderProcessor;

	public void doTask(LongEvent event, long sequence, boolean endOfBatch) {
		long orderId = event.getValue();
		try {
			OrderDTO dto = this.orderService.getFull(orderId);
			if (dto.getStatus() != OrderStatus.SAVED) {
				logger.debug("Order {} with status {} could not be processed!", dto.getId(), dto.getStatus());
				return;
			}
			orderProcessor.process(dto);
		} catch (Exception e) {
			logger.error("consume order :" + orderId + " exception occurred!", e);
		}
	}

}
