package com.woyao.admin.shop.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.product.ChatMsgDTO;
import com.woyao.admin.dto.product.QueryChatMsgRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IChatMsgAdminService;
import com.woyao.domain.chat.ChatMsg;

@Controller
@RequestMapping(value = "/shop/admin/chatMsg")
public class ShopChatMsgController extends AbstractBaseController<ChatMsg, ChatMsgDTO> {

	@Resource(name = "chatMsgAdminService")
	private IChatMsgAdminService service;
	
	@Autowired
	private ShopRoot shopRoot;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ChatMsgDTO> query(QueryChatMsgRequestDTO request) {
		Long shopId=shopRoot.getCurrentShop().getId();
		request.setShopId(shopId);
		PaginationBean<ChatMsgDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ChatMsgDTO saveOrUpdate(ChatMsgDTO dto) {
		System.out.println(dto);
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("chatMsgAdminService") IAdminService<ChatMsg, ChatMsgDTO> baseService) {
		this.baseService = baseService;
	}

}
