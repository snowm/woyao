package com.woyao.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class SharedHttpSessionContext {

	private static Map<String, HttpSession> container = new ConcurrentHashMap<>();

	private static Log log = LogFactory.getLog(SharedHttpSessionContext.class);
	
	public static void addSession(HttpSession session) {
		log.debug("addSession");
		if (session != null) {
			container.put(session.getId(), session);
		}
	}

	public static void removeSession(String id) {
		log.debug("removeSession");
		if (id != null) {
			container.remove(id);
		}
	}

	public static HttpSession getSession(String id) {
		log.debug("getSession");
		if (id != null) {
			return container.get(id);
		}
		return null;
	}
}
