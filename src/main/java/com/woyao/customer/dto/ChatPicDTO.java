package com.woyao.customer.dto;

import java.io.Serializable;

public class ChatPicDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String picUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public String toString() {
		return "ChatPicDTO [picUrl=" + picUrl + "]";
	}
	
	
}
