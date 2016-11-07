package com.woyao.admin.shop.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SMSParamsDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;// 酒吧名称

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;// 时间

	private int baTotal;// 霸屏总价

	private int baNum;// 霸屏数量

	private int liTotal;// 礼物总价

	private int liNum;// 礼物数量

	private int total;// 合计

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

	public int getBaTotal() {
		return baTotal;
	}

	public void setBaTotal(int baTotal) {
		this.baTotal = baTotal;
	}

	public int getBaNum() {
		return baNum;
	}

	public void setBaNum(int baNum) {
		this.baNum = baNum;
	}

	public int getLiTotal() {
		return liTotal;
	}

	public void setLiTotal(int liTotal) {
		this.liTotal = liTotal;
	}

	public int getLiNum() {
		return liNum;
	}

	public void setLiNum(int liNum) {
		this.liNum = liNum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
