package com.woyao.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionShareListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		SharedHttpSessionContext.addSession(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		SharedHttpSessionContext.removeSession(se.getSession().getId());
	}

}
