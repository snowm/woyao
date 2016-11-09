package com.woyao.admin.shop.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class TaoBaoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String name;//酒吧名称
	
	@JsonFormat(pattern = "yyyy年MM月dd日")
	private Date date;//时间

	@JsonFormat(shape = Shape.STRING)
	private int num1;//霸屏数量
	
	@JsonFormat(shape = Shape.STRING)
	private float money1;//霸屏总价
	
	@JsonFormat(shape = Shape.STRING)
	private int num2;//礼物数量

	@JsonFormat(shape = Shape.STRING)
	private float money2;//礼物总价

	@JsonFormat(shape = Shape.STRING)
	private float total;//合计

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

	public float getMoney1() {
		return money1;
	}

	public void setMoney1(float money1) {
		this.money1 = money1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public float getMoney2() {
		return money2;
	}

	public void setMoney2(float money2) {
		this.money2 = money2;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
}
