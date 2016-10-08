package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypeInfo {

	@JsonProperty("id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
