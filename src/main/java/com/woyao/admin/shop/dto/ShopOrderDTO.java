package com.woyao.admin.shop.dto;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderDTO {
	private Integer yearOrder;// 年

	private Integer monthOrder;// 月

	private Integer dayOrder;// 日

	private float yearTotal;// 年金额

	private float monthTotal;// 月金额

	private float dayTotal;// 日金额

	private List<ShopOrder> ShopOrders = new ArrayList<>();

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

	public float getYearTotal() {
		return yearTotal;
	}

	public void setYearTotal(float yearTotal) {
		this.yearTotal = yearTotal;
	}

	public float getMonthTotal() {
		return monthTotal;
	}

	public void setMonthTotal(float monthTotal) {
		this.monthTotal = monthTotal;
	}

	public float getDayTotal() {
		return dayTotal;
	}

	public void setDayTotal(float dayTotal) {
		this.dayTotal = dayTotal;
	}

}
