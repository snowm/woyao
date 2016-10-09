package com.woyao.customer.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.ChatterPaginationQueryRequest;
import com.woyao.customer.dto.ShopDTO;
import com.woyao.customer.dto.ShopPaginationQueryRequest;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.impl.MobileServiceImpl;

@Controller
@RequestMapping(value = "/m")
public class MobileController {

	@Resource(name = "mobileService")
	private MobileServiceImpl mobileService;

	@Resource(name = "chatService")
	private IChatService chatService;

	@RequestMapping(value = { "/", "" })
	public String index() {
		return "mobile/index";
	}

	@RequestMapping(value = { "/chatRoom/{shopId}" })
	public String chatRoom(@PathVariable("shopId") Long shopId, HttpServletRequest request) {
		ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
		HttpSession session = request.getSession();
		long roomId = room != null ? room.getId() : shopId;
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);

		return "mobile/chatRoom";
	}

	@RequestMapping(value = { "/privacyChat" })
	public String privacyChat(@RequestParam("toId") Long toId) {
		return "mobile/privacyChat";
	}

	@RequestMapping(value = { "/shopList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(ShopPaginationQueryRequest request) {
		return this.mobileService.findShop(request.getLatitude(), request.getLongitude(), request.getPageNumber(), request.getPageSize());
	}

	@RequestMapping(value = { "/chat/chatterList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ChatterDTO> chatterList(ChatterPaginationQueryRequest request) {
		PaginationBean<ChatterDTO> rs = this.chatService.listOnlineChatters(request.getChatRoomId(), request.getPageNumber(),
				request.getPageSize());
		rs.getPageNumber();
		return rs;
	}

	@RequestMapping(value = { "/chat/richerList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ChatterDTO> richerList(ChatterPaginationQueryRequest request) {
		return null;
	}

}
