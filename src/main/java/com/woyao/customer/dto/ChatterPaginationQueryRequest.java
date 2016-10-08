package com.woyao.customer.dto;

import com.woyao.PaginationQueryRequestDTO;

public class ChatterPaginationQueryRequest extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 1L;

	private long chatRoomId;

	public long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

}
