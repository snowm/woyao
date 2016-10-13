package com.woyao.customer.dto.chat.out;

import java.io.IOException;
import java.util.Date;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.woyao.admin.dto.DTOConfig;
import com.woyao.utils.JsonUtils;

public abstract class Outbound {

	protected String command;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationDate = new Date();

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date sentDate = new Date();

	@JsonIgnore
	private transient String content;

	@JsonIgnore
	private transient boolean contentGenerated = false;

	protected Outbound() {
	}

	public void send(WebSocketSession session) throws IOException {
		session.sendMessage(new TextMessage(this.getContent()));
	}

	public String getContent() {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

}
