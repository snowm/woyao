package com.woyao.customer.dto.chat.out;

public class OutDeleteMsgDTO extends Outbound {

	private Long msgId;

	@Override
	public Outbound newObject() {
		return new OutDeleteMsgDTO();
	}
	
	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

}
