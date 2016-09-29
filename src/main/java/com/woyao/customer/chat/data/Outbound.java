package com.woyao.customer.chat.data;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.dto.MsgDTO;

public abstract class Outbound {

	protected MsgDTO msg;
	
	protected Outbound() {
	}
	
	public void send(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(Inbound.om.writeValueAsString(this.msg)));
	}
}
