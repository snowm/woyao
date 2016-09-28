package com.woyao.mobile;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.customer.dto.ShopDTO;

@Controller
@RequestMapping(value = "/mobile")
public class MobileController {

	@Resource(name = "mobileService")
	private MobileService mobileService;

	@RequestMapping(value = {
			"/shop/{name}/{pageNumber}/{pageSize}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(@PathVariable("name") String name, @PathVariable("pageNumber") long pageNumber,
			@PathVariable("pageSize") int pageSize) {
//		try {
//			Thread.sleep(1000*1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return this.mobileService.findShop(name, pageNumber, pageSize);
	}
	
	@RequestMapping(value = { "/shop" }, method = { RequestMethod.POST,
			RequestMethod.PUT }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ShopDTO saveShop(ShopDTO dto) {
		return null;
	}
}
