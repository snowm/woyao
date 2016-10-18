package com.woyao.customer.dto.chat.in;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.woyao.customer.dto.chat.BlockDTO;

public class InMsgBlockDTO extends ChatMsgDTO {

	private BlockDTO block;

	public BlockDTO getBlock() {
		return block;
	}

	public void setBlock(BlockDTO block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
