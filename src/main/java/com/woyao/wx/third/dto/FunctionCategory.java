package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionCategory {

	@JsonProperty("id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
