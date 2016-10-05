package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ChatRoomDTO;
import com.woyao.admin.dto.product.QueryChatRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IChatAdminService;
import com.woyao.domain.chat.ChatRoom;

@Controller
@RequestMapping(value = "/admin/chat")
public class ChatAdminController extends AbstractBaseController<ChatRoom, ChatRoomDTO> {

	@Resource(name = "chatAdminService")
	private IChatAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ChatRoomDTO> query(QueryChatRequestDTO request) {
		PaginationBean<ChatRoomDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ChatRoomDTO saveOrUpdate(@RequestBody ChatRoomDTO dto) {
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("chatAdminService") IAdminService<ChatRoom, ChatRoomDTO> baseService) {
		this.baseService = baseService;
	}

}
