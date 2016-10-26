package com.woyao.admin.shop.dto;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderDTO {
	private Integer yearOrder;//年
	
	private Integer monthOrder;//月
	
	private Integer dayOrder;//日
	
	private Integer yearTotal;//年金额
	
	private Integer monthTotal;//月金额
	
	private Integer dayTotal;//日金额
	
	private List<ShopOrder> ShopOrders=new ArrayList<>();
	
	public List<ShopOrder> getShopOrders() {
		return ShopOrders;
	}

	public void setShopOrders(List<ShopOrder> shopOrders) {
		ShopOrders = shopOrders;
	}

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

	public Integer getYearTotal() {
		return yearTotal;
	}

	public void setYearTotal(Integer yearTotal) {
		this.yearTotal = yearTotal;
	}

	public Integer getMonthTotal() {
		return monthTotal;
	}

	public void setMonthTotal(Integer monthTotal) {
		this.monthTotal = monthTotal;
	}

	public Integer getDayTotal() {
		return dayTotal;
	}

	public void setDayTotal(Integer dayTotal) {
		this.dayTotal = dayTotal;
	}

	
	
}
