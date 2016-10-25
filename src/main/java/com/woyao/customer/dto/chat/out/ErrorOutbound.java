package com.woyao.customer.dto.chat.out;

public class ErrorOutbound extends Outbound {

	private String reason;
	
	public ErrorOutbound(){
		super();
		this.command = OutboundCommand.ERROR_MSG;
	}

	@Override
	public Outbound newObject() {
		return new ErrorOutbound();
	}
	
	public ErrorOutbound(String reason) {
		this();
		this.reason = reason;
	}
	
	public String getReason() {
		return this.reason;
	}
}
