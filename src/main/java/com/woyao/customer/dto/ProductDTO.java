package com.woyao.customer.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 2260608506954249912L;

	private Long id;

	private String name;

	private String description;

	private long unitPrice;

	private String picURL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(long unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

}
