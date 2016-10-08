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
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ShopDTO;

@Controller
@RequestMapping(value = "/m")
public class MobileController {

	@Resource(name = "mobileService")
	private MobileService mobileService;

	@RequestMapping(value = { "/", "" })
	public String index() {
		return "mobile/index";
	}

	@RequestMapping(value = { "/chatRoom/{shopId}" })
	public String chatRoom(@PathVariable("shopId") Long shopId, HttpServletRequest request) {
		ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
		HttpSession session = request.getSession();
		long roomId = room != null ? room.getId() : 1L;
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);

		return "mobile/chatRoom";
	}

	@RequestMapping(value = { "/chatterList" })
	public String chatterList(@RequestParam("shopId") Long shopId) {
		return "mobile/chatterList";
	}

	@RequestMapping(value = { "/privacyChat" })
	public String privacyChat(@RequestParam("toId") Long toId) {
		return "mobile/privacyChat";
	}

	@RequestMapping(value = { "/richer" })
	public String richer(@RequestParam("shopId") Long shopId) {
		return "mobile/richer";
	}

	@RequestMapping(value = { "/shop" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(PaginationQueryRequestDTO queryRequest) {
		try {
			Thread.sleep(100 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.mobileService.findShop(queryRequest.getPageNumber(), queryRequest.getPageSize());
	}

	@RequestMapping(value = { "/test" })
	@ResponseBody
	public PaginationBean<ShopDTO> test() {
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
