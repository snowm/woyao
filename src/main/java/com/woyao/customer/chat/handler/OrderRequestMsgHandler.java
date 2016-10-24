package com.woyao.customer.chat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.dto.chat.in.ChatMsgDTO;

@Component("orderRequestMsgHandler")
public class OrderRequestMsgHandler implements MsgHandler<ChatMsgDTO> {

	@Override
	public void handle(WebSocketSession wsSession, ChatMsgDTO inbound) {

	}

}
