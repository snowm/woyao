package com.woyao.customer.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.customer.dto.ShopDTO;

@Controller
@RequestMapping(value = "/m")
public class MobileController {

	@Resource(name = "mobileService")
	private MobileService mobileService;
	
	@RequestMapping(value={"/", ""})
	public String index(){
		return "mobile/index";
	}

	@RequestMapping(value = { "/chatRoom", "" })
	public String chatRoom(Long shopId) {
		return "mobile/chatRoom";
	}
	
	@RequestMapping(value={"/chatterList"})
	public String chatterList(@RequestParam("shopId") Long shopId){
		return "mobile/chatterList";
	}

	@RequestMapping(value={"/privacyChat"})
	public String privacyChat(@RequestParam("toId") Long toId){
		return "mobile/privacyChat";
	}

	@RequestMapping(value={"/richer"})
	public String richer(@RequestParam("shopId") Long shopId){
		return "mobile/richer";
	}

	@RequestMapping(value = { "/shop" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(PaginationQueryRequestDTO queryRequest) {
		try {
			Thread.sleep(100*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.mobileService.findShop(queryRequest.getPageNumber(), queryRequest.getPageSize());
	}

	@RequestMapping(value = { "/test" })
	@ResponseBody
	public PaginationBean<ShopDTO> test() {
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
