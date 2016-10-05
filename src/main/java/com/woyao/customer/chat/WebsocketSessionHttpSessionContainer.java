package com.woyao.customer.chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebsocketSessionHttpSessionContainer {

	private Map<String, Long> container = new ConcurrentHashMap<>();
	
}
