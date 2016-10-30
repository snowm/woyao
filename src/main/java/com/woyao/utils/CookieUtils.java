package com.woyao.utils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.woyao.GlobalConfig;
import com.woyao.security.CookieConstants;

@Component("cookieUtils")
public class CookieUtils {

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	private String getCookieName(String name) {
		return "__" + globalConfig.getEnv() + "_" + name;
	}

	public String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		for (Cookie c : cookies) {
			String cookieName = getCookieName(name);
			if (c.getName().equals(cookieName)) {
				return c.getValue();
			}
		}
		return null;
	}

	public void deleteCookie(HttpServletResponse response, String name) {
		String cookieName = getCookieName(name);
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
	}

	public void setCookie(HttpServletResponse response, String name, String value) {
		String cookieName = getCookieName(name);
		setCookie(response, cookieName, value, CookieConstants.DEFAULT_AGE, CookieConstants.DEFAULT_PATH);
	}

	public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
		String cookieName = getCookieName(name);
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
}
