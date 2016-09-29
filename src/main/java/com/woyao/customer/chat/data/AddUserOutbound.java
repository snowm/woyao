package com.woyao.customer.chat.data;

public class AddUserOutbound extends Outbound {

	private String nickName;
	
	public AddUserOutbound(String nickName) {
		super("add-user");
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}
}
