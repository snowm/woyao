package com.woyao.mobile;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.ShopDTO;
import com.woyao.domain.User;

@Controller
@RequestMapping(value = "/mobile")
public class MobileController {

	@Resource(name = "mobileService")
	private MobileService mobileService;
	@Resource(name="userService")
	private UserService userService;

	@RequestMapping(value = {
			"/shop/{name}/{pageNumber}/{pageSize}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ShopDTO> findShop(@PathVariable("name") String name, @PathVariable("pageNumber") long pageNumber,
			@PathVariable("pageSize") int pageSize) {
		return this.mobileService.findShop(name, pageNumber, pageSize);
	}
	@RequestMapping(value = {
	"/getUser/{userId}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public User getUser(@PathVariable("userId") Integer userId){
		
		return this.userService.getUser(userId);
	}
}
