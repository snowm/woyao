package com.woyao.admin.shop.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SMSParamsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;// 酒吧名称

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;// 时间

	private float baTotal = 0;// 霸屏总价

	private int baNum;// 霸屏数量

	private float liTotal = 0;// 礼物总价

	private int liNum;// 礼物数量

	private float total = 0;// 合计

	private String phone;

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

	public float getBaTotal() {
		return baTotal;
	}

	public void setBaTotal(float baTotal) {
		this.baTotal = baTotal;
	}

	public int getBaNum() {
		return baNum;
	}

	public void setBaNum(int baNum) {
		this.baNum = baNum;
	}

	public float getLiTotal() {
		return liTotal;
	}

	public void setLiTotal(float liTotal) {
		this.liTotal = liTotal;
	}

	public int getLiNum() {
		return liNum;
	}

	public void setLiNum(int liNum) {
		this.liNum = liNum;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
