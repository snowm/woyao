package com.woyao.admin.shop.dto;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.OrderDTO;

public class OrderStatisticsBean extends PaginationBean<OrderDTO> {

	private static final long serialVersionUID = 1L;

	private long msgCount;

	private float msgTotalAmount;

	public OrderStatisticsBean() {
		super();
	}

	public OrderStatisticsBean(long pageNumber, int pageSize) {
		super(pageNumber, pageSize);
	}

	public long getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(long msgCount) {
		this.msgCount = msgCount;
	}

	public float getMsgTotalAmount() {
		return msgTotalAmount;
	}

	public void setMsgTotalAmount(float msgTotalAmount) {
		this.msgTotalAmount = msgTotalAmount;
	}

}
