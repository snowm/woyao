package com.woyao.customer.dto.chat.out;

import com.woyao.customer.dto.ProfileDTO;

public class OutMsgDTO extends Outbound {

	private Long id;

	private Long clientMsgId;

	private ProfileDTO sender;

	private ProfileDTO to;

	private String text;

	private String pic;

	private long duration = 0;

	private String effectCode;

	private boolean isPrivacy = false;

	@Override
	public Outbound newObject() {
		return new OutMsgDTO();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProfileDTO getSender() {
		return sender;
	}

	public void setSender(ProfileDTO sender) {
		this.sender = sender;
	}

	public ProfileDTO getTo() {
		return to;
	}

	public void setTo(ProfileDTO to) {
		this.to = to;
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

	public String getEffectCode() {
		return effectCode;
	}

	public void setEffectCode(String effectCode) {
		this.effectCode = effectCode;
	}

	public Long getClientMsgId() {
		return clientMsgId;
	}

	public void setClientMsgId(Long clientMsgId) {
		this.clientMsgId = clientMsgId;
	}

}
