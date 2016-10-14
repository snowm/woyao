package com.woyao.customer.chat.handler;

import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.dto.chat.in.Inbound;

public interface MsgHandler<T extends Inbound> {

	void handle(WebSocketSession wsSession, T inbound);
}
