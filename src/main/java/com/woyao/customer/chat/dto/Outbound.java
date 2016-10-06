package com.woyao.customer.chat.dto;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.JsonUtils;

public abstract class Outbound {

	protected String command;

	protected Outbound() {
	}

	public void send(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(JsonUtils.toString(this)));
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
