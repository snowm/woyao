package com.woyao.customer.chat.data;

public class ErrorOutbound extends Outbound {

	private String reason;
	
	public ErrorOutbound(String reason) {
		super("error");
		this.reason = reason;
	}
	
	public String getReason() {
		return this.reason;
	}
}
