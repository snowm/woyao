package com.woyao.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

public class SharedSessionContext {

	private static Map<String, HttpSession> container = new ConcurrentHashMap<>();

	public static void addSession(HttpSession session) {
		if (session != null) {
			container.put(session.getId(), session);
		}

	}

	public static void removeSession(String id) {
		if (id != null) {
			container.remove(id);
		}
	}

	public static HttpSession getSession(String id) {
		if (id != null) {
			return container.get(id);
		}
		return null;
	}
}
