package com.woyao.customer.dto.chat.out;

import com.woyao.customer.dto.ChatterDTO;

public class SelfChatterInfoDTO extends Outbound {

	private ChatterDTO self;

	public SelfChatterInfoDTO() {
		super();
		this.command = OutboundCommand.SELF_INFO_MSG;
	}

	public ChatterDTO getSelf() {
		return self;
	}

	public void setSelf(ChatterDTO self) {
		this.self = self;
	}

}
