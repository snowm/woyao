package com.woyao.wx.dto;

import java.io.Serializable;

public class ProductDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String goods_id;
	
	private String wxpay_goods_id;
	
	private String goods_name;
	
	private int quantity;
	
	private int price;
	
	private String body;
	
	private String goods_category;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getWxpay_goods_id() {
		return wxpay_goods_id;
	}

	public void setWxpay_goods_id(String wxpay_goods_id) {
		this.wxpay_goods_id = wxpay_goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getGoods_category() {
		return goods_category;
	}

	public void setGoods_category(String goods_category) {
		this.goods_category = goods_category;
	}
	
	
}
