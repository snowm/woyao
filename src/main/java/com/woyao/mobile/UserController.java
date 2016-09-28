package com.woyao.mobile;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woyao.domain.User;

@Controller
public class UserController {
	@Resource(name="userService")
	private UserService userService;
	@RequestMapping(value = {
	"/getUser/{userId}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public User getUser(@PathVariable("userId") String userId){
		
		return this.userService.getUser(userId);
	}
}
