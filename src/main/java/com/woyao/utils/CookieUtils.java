package com.woyao.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static final String COOKIE_CHATTER_ID = "_ctWY_id";

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		for (Cookie c : cookies) {
			if (c.getName().equals(COOKIE_CHATTER_ID)) {
				return c.getValue();
			}
		}
		return null;
	}
	
	public static void setCookie(HttpServletResponse response, String name, String value){
		Cookie cookie = new Cookie(name, value);
		response.addCookie(cookie);
	}
}
