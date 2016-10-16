package com.woyao.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!logger.isDebugEnabled()) {
			return true;
		}
		String requestUri = request.getRequestURI();
		StringBuilder sb = new StringBuilder();
		sb.append("request uri:").append(requestUri).append(" ");
		Enumeration<String> a = request.getParameterNames();
		while (a.hasMoreElements()) {
			String key = a.nextElement();
			sb.append(key + ":" + request.getParameter(key) + ", ");
		}
		logger.debug(sb.toString());
		
		sb = new StringBuilder("===headers===\n");
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String name = headerNames.nextElement();
			sb.append(name).append(":").append(request.getHeader(name)).append("\n");
		}
		logger.debug(sb.toString());
		return true;
	}

}
