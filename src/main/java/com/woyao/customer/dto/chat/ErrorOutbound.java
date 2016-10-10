package com.woyao.customer.dto.chat;

public class ErrorOutbound extends Outbound {

	private String reason;
	
	public ErrorOutbound(String reason) {
		super();
		this.reason = reason;
	}
	
	public String getReason() {
		return this.reason;
	}
}
