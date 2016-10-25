package com.woyao.admin.shop.dto;

import java.math.BigDecimal;

public class ShopOrder {
	private Integer yearOrder;
	
	private Integer monthOrder;
	
	private Integer dayOrder;
	
	private BigDecimal totalOrder;

	public Integer getYearOrder() {
		return yearOrder;
	}

	public void setYearOrder(Integer yearOrder) {
		this.yearOrder = yearOrder;
	}

	public Integer getMonthOrder() {
		return monthOrder;
	}

	public void setMonthOrder(Integer monthOrder) {
		this.monthOrder = monthOrder;
	}

	public Integer getDayOrder() {
		return dayOrder;
	}

	public void setDayOrder(Integer dayOrder) {
		this.dayOrder = dayOrder;
	}

	public BigDecimal getTotalOrder() {
		return totalOrder;
	}

	public void setTotalOrder(BigDecimal totalOrder) {
		this.totalOrder = totalOrder;
	}
	
	
}
