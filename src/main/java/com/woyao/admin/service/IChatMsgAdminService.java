package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ChatMsgDTO;
import com.woyao.admin.dto.product.QueryChatMsgRequestDTO;
import com.woyao.domain.chat.ChatMsg;

public interface IChatMsgAdminService extends IAdminService<ChatMsg, ChatMsgDTO>{

	PaginationBean<ChatMsgDTO> query(QueryChatMsgRequestDTO request);
	
}
