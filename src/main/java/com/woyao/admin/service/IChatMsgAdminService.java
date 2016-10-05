package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ChatMsgDTO;
import com.woyao.admin.dto.product.ChatRoomDTO;
import com.woyao.admin.dto.product.QueryChatMsgRequestDTO;
import com.woyao.admin.dto.product.QueryChatRequestDTO;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.chat.ChatRoom;

public interface IChatMsgAdminService extends IAdminService<ChatMsg, ChatMsgDTO>{

	PaginationBean<ChatMsgDTO> query(QueryChatMsgRequestDTO request);
	
}
