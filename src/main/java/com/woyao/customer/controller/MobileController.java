package com.woyao.customer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.GlobalConfig;
import com.woyao.customer.chat.SessionContainer;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.ChatPicDTO;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.ChatterQueryRequest;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;
import com.woyao.customer.dto.RicherDTO;
import com.woyao.customer.dto.ShopDTO;
import com.woyao.customer.dto.ShopPaginationQueryRequest;
import com.woyao.customer.dto.chat.MsgQueryRequest;
import com.woyao.customer.dto.chat.out.OutMsgDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IMobileService;
import com.woyao.customer.service.IProductService;
import com.woyao.domain.wx.JsapiTicket;
import com.woyao.service.JsapiTicketService;
import com.woyao.utils.UrlUtils;
import com.woyao.wx.WxUtils;

@Controller
@RequestMapping(value = "/m")
public class MobileController {

	@Resource(name = "mobileService")
	private IMobileService mobileService;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Resource(name = "productService")
	private IProductService productService;

	@Resource(name = "jsapiTicketService")
	private JsapiTicketService jsapiTicketService;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@RequestMapping(value = { "/", "" })
	public String index(HttpServletRequest httpRequest) {
		generateJsapiToken(httpRequest);
		return "mobile/index";
	}

	@RequestMapping(value = { "/chatRoom/{shopId}" })
	public String chatRoom(@PathVariable("shopId") long shopId, HttpServletRequest httpRequest) {
		generateJsapiToken(httpRequest);
		HttpSession session = httpRequest.getSession();
		session.setAttribute(SessionContainer.SESSION_ATTR_SHOP_ID, shopId);

		ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
		long roomId = room != null ? room.getId() : shopId;
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);

		ChatterDTO chatter = SessionUtils.getChatter(session);
		chatter.setDistanceToRoom(this.mobileService.calculateDistanceToShop(chatter.getLatitude(), chatter.getLongitude(), shopId));
		return "mobile/chatRoom";
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
		HttpSession session = httpRequest.getSession();
		Long chatRoomId = SessionUtils.getChatRoomId(session);
		Long chatterId = SessionUtils.getChatterId(session);

		PaginationBean<ChatterDTO> rs = this.chatService.listOnlineChatters(chatterId, chatRoomId, request.getGender(),
				request.getPageNumber(), request.getPageSize());
		return rs;
	}

	@RequestMapping(value = { "/chat/richerList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<RicherDTO> listRicher(ChatterQueryRequest request, HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		Long chatRoomId = SessionUtils.getChatRoomId(session);
		Long chatterId = SessionUtils.getChatterId(session);

		PaginationBean<ChatterDTO> rs = this.chatService.listOnlineChatters(chatterId, chatRoomId, request.getGender(),
				request.getPageNumber(), request.getPageSize());
		rs.getPageNumber();
		PaginationBean<RicherDTO> pb = new PaginationBean<>();
		pb.setPageNumber(rs.getPageNumber());
		pb.setPageSize(rs.getPageSize());
		pb.setTotalCount(rs.getTotalCount());
		List<RicherDTO> list = new ArrayList<>();
		if (rs.getResults() != null) {
			for (ChatterDTO c : rs.getResults()) {
				RicherDTO r = new RicherDTO();
				r.setChatterDTO(c);
				r.setPayMsgCount(10);
				list.add(r);
			}
			pb.setResults(list);
		}
		return pb;
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
	public List<OutMsgDTO> listHistoryMsg(MsgQueryRequest request, HttpServletRequest httpRequest) {
		Long chatRoomId = SessionUtils.getChatRoomId(httpRequest.getSession());
		Long chatterId = SessionUtils.getChatterId(httpRequest.getSession());
		request.setChatRoomId(chatRoomId);
		request.setSelfChatterId(chatterId);
		return this.chatService.listHistoryMsg(request);
	}

	@RequestMapping(value = {
			"/chat/chatPicList/{id}/{pageNumber}/{pageSize}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<ChatPicDTO> getChatPicList(@PathVariable("id") Long id, @PathVariable("pageNumber") Long pageNumber,
			@PathVariable("pageSize") Integer pageSize) {

		return this.chatService.getPicUrl(id, pageNumber, pageSize);
	}

	private void generateJsapiToken(HttpServletRequest httpRequest) {
		JsapiTicket ticket = this.jsapiTicketService.getToken();
		List<NameValuePair> nvs = new ArrayList<>();
		String timestamp = WxUtils.generateTimestamp();
		String nonceStr = WxUtils.generateNonce(32);
		try {
			nvs.add(WxUtils.generateNVPair("timestamp", timestamp));
			nvs.add(WxUtils.generateNVPair("noncestr", nonceStr));
			nvs.add(WxUtils.generateNVPair("jsapi_ticket", ticket.getTicket()));
			String url = UrlUtils.calculateCurrentUri(httpRequest);
			nvs.add(WxUtils.generateNVPair("url", url));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		httpRequest.setAttribute("appId", globalConfig.getAppId());
		httpRequest.setAttribute("timestamp", timestamp);
		httpRequest.setAttribute("nonceStr", nonceStr);
		httpRequest.setAttribute("signature", WxUtils.generateJsapiSign(nvs));
	}
}
