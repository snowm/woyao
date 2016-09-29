package com.woyao.customer.chat.data;

public class MessageOutbound extends Outbound {

	private String from;
	
	private String to;
	
	private String message;

	public MessageOutbound(String from, String message) {
		this(from, null, message);
	}
	
	public MessageOutbound(String from, String to, String message) {
		super("message");
		this.from = from;
		this.to = to;
		this.message = message;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}

	public String getMessage() {
		return message;
	}
}
