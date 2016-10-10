package com.woyao.customer.dto.chat;

import com.woyao.customer.dto.ChatterDTO;

public class OutMsgDTO extends Outbound {

	private Long id;

	private ChatterDTO sender;

	private String text;

	private String pic;

	private long duration = 0;

	private boolean isPrivacy = false;

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

	public boolean isPrivacy() {
		return isPrivacy;
	}

	public void setPrivacy(boolean isPrivacy) {
		this.isPrivacy = isPrivacy;
	}

}