package com.woyao.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ChatRoomDTO;
import com.woyao.admin.dto.product.QueryChatRequestDTO;
import com.woyao.admin.service.IChatAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatRoom;
@Service("chatAdminService")
public class IChatAdminServiceImpl extends AbstractAdminService<ChatRoom, ChatRoomDTO> implements IChatAdminService{

	@Resource(name = "commonDao")
	private CommonDao dao;

	public ChatRoomDTO update(ChatRoomDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatRoom transferToDomain(ChatRoomDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatRoomDTO transferToSimpleDTO(ChatRoom m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChatRoomDTO transferToFullDTO(ChatRoom m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationBean<ChatRoomDTO> query(QueryChatRequestDTO request) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
