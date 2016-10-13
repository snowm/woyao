package com.woyao.customer.dto.chat.out;

import com.woyao.customer.dto.ChatterDTO;

public class ChatRoomInfoDTO extends Outbound {

	private ChatterDTO richer;

	private Integer totalNumber;

	public ChatRoomInfoDTO() {
		super();
		this.command = OutboundCommand.ROOM_INFO_MSG;
	}

	public ChatterDTO getRicher() {
		return richer;
	}

	public void setRicher(ChatterDTO richer) {
		this.richer = richer;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

}
