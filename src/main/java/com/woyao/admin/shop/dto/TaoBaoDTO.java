package com.woyao.admin.shop.dto;

import java.io.Serializable;
import java.util.Date;

public class TaoBaoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;//酒吧名称
	
	private Date date;//时间
	
	private int num1;//霸屏总价
	
	private int money1;//霸屏数量
	
	private int num2;//礼物数量
	
	private int money2;//礼物总价
	
	private int total;//合计

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int getMoney1() {
		return money1;
	}

	public void setMoney1(int money1) {
		this.money1 = money1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public int getMoney2() {
		return money2;
	}

	public void setMoney2(int money2) {
		this.money2 = money2;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
