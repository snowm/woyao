package com.woyao.customer.dto.chat.in;

import com.woyao.customer.dto.chat.BlockDTO;

public class InMsgDTO extends Inbound {

	private Long msgId;

	private Long to;

	private BlockDTO block;

	private int blockSize;

	private Long productId;

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public BlockDTO getBlock() {
		return block;
	}

	public void setBlock(BlockDTO block) {
		this.block = block;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
