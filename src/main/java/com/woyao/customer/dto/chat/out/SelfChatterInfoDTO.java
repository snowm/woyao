package com.woyao.customer.dto.chat.out;

import com.woyao.customer.dto.ProfileDTO;

public class SelfChatterInfoDTO extends Outbound {

	private ProfileDTO self;

	public SelfChatterInfoDTO() {
		super();
		this.command = OutboundCommand.SELF_INFO_MSG;
	}

	@Override
	public Outbound newObject() {
		return new SelfChatterInfoDTO();
	}
	
	public ProfileDTO getSelf() {
		return self;
	}

	public void setSelf(ProfileDTO self) {
		this.self = self;
	}

}
