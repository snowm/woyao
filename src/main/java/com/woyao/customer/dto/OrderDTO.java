package com.woyao.customer.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woyao.admin.dto.DTOConfig;

public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private ChatterDTO payer;

	private ChatterDTO consumer;

	private List<OrderItemDTO> items;

	private long totalFee;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationDate = new Date();

	public ChatterDTO getPayer() {
		return payer;
	}

	public void setPayer(ChatterDTO payer) {
		this.payer = payer;
	}

	public ChatterDTO getConsumer() {
		return consumer;
	}

	public void setConsumer(ChatterDTO consumer) {
		this.consumer = consumer;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(long totalFee) {
		this.totalFee = totalFee;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
