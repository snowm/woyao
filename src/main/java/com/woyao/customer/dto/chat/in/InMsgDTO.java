package com.woyao.customer.dto.chat.in;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.woyao.customer.dto.chat.BlockDTO;

public class InMsgDTO extends Inbound {

	private Long to;

	private BlockDTO block;

	private int blockSize;

	private Long productId;

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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
