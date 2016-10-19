package com.woyao.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.snowm.security.core.userdetails.ExtSecurityUser;
import com.snowm.security.profile.domain.Profile;
import com.snowm.security.web.utils.SecurityUtils;

public class SelfSecurityUtils {

	private static Log log = LogFactory.getLog(SecurityUtils.class);

	private static final String LOGIN_CLIENT = "_loginClient";

	public static final String LOGIN_CLIENT_JS = "js";

	public static final String LOGIN_CLIENT_BROWSER = "browser";

	public static boolean isAnonymous() {
		return getCurrentUserName() == null;
	}

	public static Profile getCurrentProfile() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null) {
			return null;
		}
		if (principal instanceof ExtSecurityUser) {
			ExtSecurityUser userDetails = (ExtSecurityUser) principal;
			return userDetails.getProfile();
		} else {
			return null;
		}
	}

	public static String getCurrentUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null) {
			return null;
		}
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			return userDetails.getUsername();
		} else {
			return principal.toString();
		}
	}

	/**
	 * Gets the current authorities.
	 *
	 * @return the current authorities
	 */
	public static Collection<? extends GrantedAuthority> getCurrentAuthorities() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			return userDetails.getAuthorities();
		}
		return new ArrayList<SimpleGrantedAuthority>();
	}

	public static boolean hasAuthority(String authority) {
		Collection<? extends GrantedAuthority> currentAuthorities = getCurrentAuthorities();
		if (CollectionUtils.isEmpty(currentAuthorities)) {
			return false;
		}
		for (GrantedAuthority a : currentAuthorities) {
			if (a.getAuthority().equals(authority)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		List<MediaType> accepts = MediaType.parseMediaTypes(request.getHeader(HttpHeaders.ACCEPT));
		if (accepts != null && !accepts.isEmpty()) {
			if (accepts.contains(MediaType.APPLICATION_JSON) || accepts.contains(MediaType.APPLICATION_JSON_UTF8)) {
				return true;
			}
		}
		String loginClient = request.getParameter(LOGIN_CLIENT);
		if (StringUtils.isEmpty(loginClient)) {
			return false;
		}
		if (LOGIN_CLIENT_JS.equalsIgnoreCase(loginClient)) {
			return true;
		}
		return false;
	}

	public static boolean putJsonContent(HttpServletResponse response, String jsonContent) {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		try {
			response.getWriter().write(jsonContent);
		} catch (IOException e) {
			if (log.isWarnEnabled()) {
				log.warn("Write json content error!", e);
			}
			return false;
		}
		return true;
	}

}
