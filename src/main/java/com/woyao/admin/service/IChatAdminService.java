package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ChatRoomDTO;
import com.woyao.admin.dto.product.QueryChatRequestDTO;
import com.woyao.domain.chat.ChatRoom;

public interface IChatAdminService extends IAdminService<ChatRoom, ChatRoomDTO>{

	PaginationBean<ChatRoomDTO> query(QueryChatRequestDTO request);
	
}
