package com.woyao.customer.chat.data;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public abstract class Outbound {

	private String type;
	
	protected Outbound(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public void send(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(Inbound.om.writeValueAsString(this)));
	}
}
