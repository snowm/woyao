package com.woyao.customer.dto.chat;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woyao.JsonUtils;

public abstract class Outbound {

	protected String command;

	private transient String content;

	private transient boolean contentGenerated = false;

	protected Outbound() {
	}

	public void send(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(this.getContent()));
	}

	private String getContent() {
		if (this.contentGenerated) {
			return this.content;
		}
		synchronized (this) {
			try {
				this.content = JsonUtils.toString(this);
				return this.content;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.contentGenerated = false;
		this.command = command;
	}

}
