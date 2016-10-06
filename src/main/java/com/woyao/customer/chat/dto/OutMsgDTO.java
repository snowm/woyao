package com.woyao.customer.chat.dto;

import com.woyao.customer.dto.ChatterDTO;

public class OutMsgDTO extends Outbound {

	private Long id;

	private ChatterDTO sender;

	private Long to;

	private String text;

	private String pic;

	private long duration = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChatterDTO getSender() {
		return sender;
	}

	public void setSender(ChatterDTO sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

}
