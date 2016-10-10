package com.woyao.customer.controller;

import java.util.List;

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
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.ChatterQueryRequest;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;
import com.woyao.customer.dto.RicherDTO;
import com.woyao.customer.dto.ShopDTO;
import com.woyao.customer.dto.ShopPaginationQueryRequest;
import com.woyao.customer.dto.chat.MsgQueryRequest;
import com.woyao.customer.dto.chat.OutMsgDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IMobileService;
import com.woyao.customer.service.IProductService;

@Controller
@RequestMapping(value = "/m")
public class MobileController {

	@Resource(name = "mobileService")
	private IMobileService mobileService;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Resource(name = "productService")
	private IProductService productService;

	@RequestMapping(value = { "/", "" })
	public String index() {
		return "mobile/index";
	}

	@RequestMapping(value = { "/chatRoom/{shopId}" })
	public String chatRoom(@PathVariable("shopId") long shopId, HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		session.setAttribute(SessionContainer.SESSION_ATTR_SHOP_ID, shopId);

		ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
		long roomId = room != null ? room.getId() : shopId;
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);

		ChatterDTO chatter = SessionUtils.getChatter(session);
		chatter.setDistanceToRoom(this.mobileService.calculateDistanceToShop(chatter.getLatitude(), chatter.getLongitude(), shopId));
		return "mobile/chatRoom";
	}

	@RequestMapping(value = { "/privacyChat" })
	public String privacyChat(@RequestParam("toId") Long toId) {
		return "mobile/privacyChat";
	}

	@RequestMapping(value = { "/shopList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(ShopPaginationQueryRequest request, HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		ChatterDTO chatter = SessionUtils.getChatter(session);
		chatter.setLatitude(request.getLatitude());
		chatter.setLongitude(request.getLongitude());
		return this.mobileService.findShop(request.getLatitude(), request.getLongitude(), request.getPageNumber(), request.getPageSize());
	}

	@RequestMapping(value = { "/chat/chatterList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ChatterDTO> listChatter(ChatterQueryRequest request, HttpServletRequest httpRequest) {
		Long chatRoomId = SessionUtils.getChatRoomId(httpRequest.getSession());
		PaginationBean<ChatterDTO> rs = this.chatService.listOnlineChatters(chatRoomId, request.getGender(), request.getPageNumber(),
				request.getPageSize());
		rs.getPageNumber();
		return rs;
	}

	@RequestMapping(value = { "/chat/richerList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<RicherDTO> listRicher(ChatterQueryRequest request, HttpServletRequest httpRequest) {
		Long chatRoomId = SessionUtils.getChatRoomId(httpRequest.getSession());
		// PaginationBean<ChatterDTO> rs =
		// this.chatService.listOnlineChatters(chatRoomId, request.getGender(),
		// request.getPageNumber(),
		// request.getPageSize());
		// rs.getPageNumber();
		return new PaginationBean<RicherDTO>();
	}

	@RequestMapping(value = { "/chat/msgProductList" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<MsgProductDTO> listMsgProduct() {
		return productService.listAllMsgProduct();
	}

	@RequestMapping(value = { "/chat/productList" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<ProductDTO> listProduct(HttpServletRequest httpRequest) {
		Long shopId = SessionUtils.getShopId(httpRequest.getSession());
		if (shopId == null) {
			throw new RuntimeException("Please select a shop!");
		}
		return productService.listProducts(shopId);
	}

	@RequestMapping(value = { "/chat/listMsg" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<OutMsgDTO> listMsg(MsgQueryRequest request, HttpServletRequest httpRequest) {
		Long shopId = SessionUtils.getShopId(httpRequest.getSession());
		Long chatterId = SessionUtils.getChatterId(httpRequest.getSession());
		request.setShopId(shopId);
		request.setSelfChatterId(chatterId);
		return this.chatService.listMsg(request);
	}
}
