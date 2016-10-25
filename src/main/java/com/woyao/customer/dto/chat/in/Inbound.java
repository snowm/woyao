package com.woyao.customer.dto.chat.in;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woyao.admin.dto.DTOConfig;
import com.woyao.utils.JsonUtils;

public abstract class Inbound {

	private static Log log = LogFactory.getLog(Inbound.class);

	private Long from;
	
	private Long msgId;

	private String remoteAddress;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationDate = new Date();

	public static Inbound parse(String payload) {
		int index = payload.indexOf('\n');
		String type, data;
		if (index == -1) {
			type = payload.trim();
			data = "";
		} else {
			type = payload.substring(0, index).trim();
			data = payload.substring(index + 1);
		}
		try {
			switch (type) {
			case "msg":
				return JsonUtils.toObject(data, ChatMsgDTO.class);
			case "msgBlock":
				return JsonUtils.toObject(data, ChatMsgBlockDTO.class);
			case "ordReq":
				return JsonUtils.toObject(data, OrderRequestDTO.class);
			default:
				return null;
			}
		} catch (IOException e) {
			log.warn("Parse in message error:" + payload, e);
			return null;
		}
	}
	
	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
