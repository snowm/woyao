package com.woyao.customer.chat.dto;

public class InMsgBlockDTO extends Inbound {

	private Long msgId;

	private BlockDTO block;

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public BlockDTO getBlock() {
		return block;
	}

	public void setBlock(BlockDTO block) {
		this.block = block;
	}

}
