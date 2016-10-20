package com.woyao.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static final String COOKIE_SHOP_ID = "__woyao_spWY_id";
	
	public static final String COOKIE_OPEN_ID = "__woyao_opWY_id";
	
	public static final String COOKIE_PATH = "/";
	
	public static final int COOKIE_AGE = 31536000;
//	public static final int COOKIE_AGE = -1;

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		for (Cookie c : cookies) {
			if (c.getName().equals(name)) {
				return c.getValue();
			}
		}
		return null;
	}

	public static void deleteCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
	}
	
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, COOKIE_AGE, COOKIE_PATH);
	}
	
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
}
