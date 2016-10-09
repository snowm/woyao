package com.woyao.customer.chat.dto;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woyao.JsonUtils;

public abstract class Outbound {

	protected String command;
	
	private String content;
	
	private boolean contentGenerated = false;

	protected Outbound() {
	}
	
	public void send(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(JsonUtils.toString(this)));
	}

	private synchronized String getContent() {
		if (!this.contentGenerated) {
			try {
				this.content = JsonUtils.toString(this);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return this.content;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.contentGenerated = false;
		this.command = command;
	}

}
