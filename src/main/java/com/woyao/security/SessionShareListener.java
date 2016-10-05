package com.woyao.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionShareListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		SharedSessionContext.addSession(se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		SharedSessionContext.removeSession(se.getSession().getId());
	}

}
