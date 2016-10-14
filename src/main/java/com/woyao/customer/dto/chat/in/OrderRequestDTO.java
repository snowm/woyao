package com.woyao.customer.dto.chat.in;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.woyao.customer.dto.OrderItemDTO;

public class OrderRequestDTO extends Inbound {

	// 此订单给谁，若是不填，就是自己
	private Long consumerId;

	// 此订单请求谁付，若是不填，就是自己
	private Long payerId;

	private List<OrderItemDTO> items;

	private Long totalFee;

	public Long getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Long consumerId) {
		this.consumerId = consumerId;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
