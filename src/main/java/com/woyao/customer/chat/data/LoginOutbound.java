package com.woyao.customer.chat.data;

import java.util.Collection;

public class LoginOutbound extends Outbound {

	private String nickName;
	
	private Collection<String> otherNickNames;
	
	public LoginOutbound(String nickName, Collection<String> otherNickNames) {
		super("login");
		this.nickName = nickName;
		this.otherNickNames = otherNickNames;
	}
	
	public String getNickName() {
		return this.nickName;
	}

	public Collection<String> getOtherNickNames() {
		return otherNickNames;
	}
}
