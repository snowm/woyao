package com.woyao.security;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class TrafficRequestMatcher implements RequestMatcher {
	
	private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

	private Pattern excludedURIs = Pattern.compile("^/upload.*$");
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.util.matcher.RequestMatcher#matches(javax.
	 * servlet.http.HttpServletRequest)
	 */
	public boolean matches(HttpServletRequest request) {
		if (allowedMethods.matcher(request.getMethod()).matches()){
			return true;
		}
		return !excludedURIs.matcher(request.getRequestURI()).matches();
	}

}
