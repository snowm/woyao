package com.woyao.customer.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.GlobalConfig;
import com.woyao.cache.RicherReportCache;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.chat.session.SessionContainer;
import com.woyao.customer.dto.ChatPicDTO;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ChatterQueryRequest;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;
import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.dto.RicherDTO;
import com.woyao.customer.dto.RicherType;
import com.woyao.customer.dto.ShopDTO;
import com.woyao.customer.dto.ShopPaginationQueryRequest;
import com.woyao.customer.dto.chat.MsgQueryRequest;
import com.woyao.customer.dto.chat.out.OutMsgDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.customer.service.IMobileService;
import com.woyao.customer.service.IProductService;
import com.woyao.domain.wx.JsapiTicket;
import com.woyao.security.CookieConstants;
import com.woyao.service.JsapiTicketService;
import com.woyao.utils.CookieUtils;
import com.woyao.utils.PaginationUtils;
import com.woyao.utils.UrlUtils;
import com.woyao.wx.WxUtils;

@Controller
@RequestMapping(value = "/m")
public class MobileController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "mobileService")
	private IMobileService mobileService;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Resource(name = "productService")
	private IProductService productService;

	@Resource(name = "jsapiTicketService")
	private JsapiTicketService jsapiTicketService;
	
	@Resource(name = "richerReportCache")
	private RicherReportCache richerReportCache;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@Resource(name = "cookieUtils")
	private CookieUtils cookieUtils;

	@RequestMapping(value = { "/", "" })
	public String index(HttpServletRequest httpRequest) {
		generateJsapiToken(httpRequest);
		return "mobile/index";
	}

	@RequestMapping(value = { "/chatRoom/{shopId}" }, method = RequestMethod.GET)
	public String chatRoomPortal(@PathVariable("shopId") long shopId, HttpServletRequest httpRequest, HttpServletResponse response) {
		this.cookieUtils.setCookie(response, CookieConstants.SHOP_ID, shopId + "");
		HttpSession session = httpRequest.getSession();
		session.setAttribute(SessionContainer.SESSION_ATTR_SHOP_ID, shopId);

		ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
		long roomId = room.getId();
		session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);

		ProfileDTO chatter = SessionUtils.getChatter(session);
		chatter.setDistanceToRoom(this.mobileService.calculateDistanceToShop(chatter.getLatitude(), chatter.getLongitude(), shopId));
		return "redirect:/m/chatRoom/";
	}

	@RequestMapping(value = { "/chatRoom" }, method = RequestMethod.GET)
	public String chatRoomAlias(HttpServletRequest httpRequest) {
		return "redirect:/m/chatRoom/";
	}

	@RequestMapping(value = { "/chatRoom/" }, method = RequestMethod.GET)
	public String chatRoom(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		
		Long shopId = SessionUtils.getShopId(session);
		if (shopId == null) {
			String shopIdCookieStr = this.cookieUtils.getCookie(httpRequest, CookieConstants.SHOP_ID);
			if (!StringUtils.isBlank(shopIdCookieStr)) {
				try {
					shopId = Long.parseLong(shopIdCookieStr);
					session.setAttribute(SessionContainer.SESSION_ATTR_SHOP_ID, shopId);
				} catch (NumberFormatException nfex) {
					logger.error("从cookie中解析shopId错误", nfex);
				}
			}
		}
		if (shopId == null) {
			return "redirect:/m";
		}
		Long roomId = SessionUtils.getChatRoomId(session);

		if (roomId == null) {
			ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
			roomId = room.getId();
			session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);
		}

		ProfileDTO chatter = SessionUtils.getChatter(session);
		chatter.setDistanceToRoom(this.mobileService.calculateDistanceToShop(chatter.getLatitude(), chatter.getLongitude(), shopId));

		generateJsapiToken(httpRequest);
		return "mobile/chatRoom";
	}

	@RequestMapping(value = { "/shopList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(ShopPaginationQueryRequest request, HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		ProfileDTO chatter = SessionUtils.getChatter(session);
		if (request.getLatitude() != null && request.getLongitude() != null) {
			chatter.setLatitude(request.getLatitude());
			chatter.setLongitude(request.getLongitude());
		}
		return this.mobileService.findShop(chatter.getLatitude(), chatter.getLongitude(), request.getPageNumber(), request.getPageSize());
	}

	@RequestMapping(value = { "/chat/chatterList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProfileDTO> listChatter(ChatterQueryRequest request, HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		Long chatRoomId = SessionUtils.getChatRoomId(session);
		Long chatterId = SessionUtils.getChatterId(session);

		PaginationBean<ProfileDTO> rs = this.chatService.listOnlineChatters(chatterId, chatRoomId, request.getGender(),
				request.getPageNumber(), request.getPageSize());
		return rs;
	}

	@RequestMapping(value = { "/chat/richerList" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<RicherDTO> listRicher(ChatterQueryRequest request, HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		Long chatRoomId = SessionUtils.getChatRoomId(session);
		long shopId = SessionUtils.getShopId(session);
		Long chatterId = SessionUtils.getChatterId(session);
		if (request.getRicherType() == RicherType.DAY) {
			List<RicherDTO> richers = this.richerReportCache.listDailyRichers(shopId);
			return PaginationUtils.paging(richers, request.getPageNumber(), request.getPageSize());
		}

		PaginationBean<ProfileDTO> rs = this.chatService.listOnlineChatters(chatterId, chatRoomId, request.getGender(),
				request.getPageNumber(), request.getPageSize());
		rs.getPageNumber();
		PaginationBean<RicherDTO> pb = new PaginationBean<>();
		pb.setPageNumber(rs.getPageNumber());
		pb.setPageSize(rs.getPageSize());
		pb.setTotalCount(rs.getTotalCount());
		List<RicherDTO> list = new ArrayList<>();
		if (rs.getResults() != null) {
			for (ProfileDTO c : rs.getResults()) {
				RicherDTO r = new RicherDTO();
				r.setChatterDTO(c);
				r.setPayMsgCount(10);
				list.add(r);
			}
			pb.setResults(list);
		}
		logger.debug("list richer:{}", pb);
		return pb;
	}

	@RequestMapping(value = { "/chat/msgProductList" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<MsgProductDTO> listMsgProduct(HttpServletRequest httpRequest) {
		Long shopId = SessionUtils.getShopId(httpRequest.getSession());
		return productService.listAllMsgProductFromCache(shopId);
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
		JsapiTicket ticket = this.jsapiTicketService.getTicket();
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
