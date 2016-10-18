package com.woyao.customer.disruptor;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.woyao.customer.DefaultOrderProcessor;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.service.IOrderService;

@Component("submitOrderMessageHandler")
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
			orderProcessor.process(dto);
		} catch (Exception e) {
			logger.error("consume order :" + orderId + " exception occurred!", e);
		}
	}

}
