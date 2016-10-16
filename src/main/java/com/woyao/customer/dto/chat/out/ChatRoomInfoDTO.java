package com.woyao.customer.dto.chat.out;

import com.woyao.customer.dto.ChatRoomStatistics;

public class ChatRoomInfoDTO extends Outbound {

	private ChatRoomStatistics statistics;

	public ChatRoomInfoDTO() {
		super();
		this.command = OutboundCommand.ROOM_INFO_MSG;
	}

	public ChatRoomInfoDTO(ChatRoomStatistics statistics) {
		this();
		this.statistics = statistics;
	}

	public ChatRoomStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(ChatRoomStatistics statistics) {
		this.statistics = statistics;
	}

}
