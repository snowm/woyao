package com.woyao.customer.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woyao.admin.dto.DTOConfig;
import com.woyao.domain.purchase.OrderStatus;

public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long version;

	private ChatterDTO consumer;

	private ChatterDTO toProfile;

	private List<OrderItemDTO> items;

	private int totalFee;

	private OrderStatus status;

	private String spbillCreateIp;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationDate = new Date();

	private Long msgId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public ChatterDTO getConsumer() {
		return consumer;
	}

	public void setConsumer(ChatterDTO consumer) {
		this.consumer = consumer;
	}

	public ChatterDTO getToProfile() {
		return toProfile;
	}

	public void setToProfile(ChatterDTO toProfile) {
		this.toProfile = toProfile;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
