package com.woyao.customer.dto.chat.in;

import com.woyao.customer.dto.chat.BlockDTO;

public class InMsgBlockDTO extends Inbound {

	private BlockDTO block;

	public BlockDTO getBlock() {
		return block;
	}

	public void setBlock(BlockDTO block) {
		this.block = block;
	}

}
