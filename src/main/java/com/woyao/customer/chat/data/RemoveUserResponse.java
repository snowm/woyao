package com.woyao.customer.chat.data;

public class RemoveUserResponse extends Outbound {

	private String nickName;
	
	public RemoveUserResponse(String nickName) {
		super("remove-user");
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}
}
