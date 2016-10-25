package com.woyao.customer.chat.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.dto.chat.in.ChatMsgBlockDTO;
import com.woyao.customer.dto.chat.in.ChatMsgDTO;
import com.woyao.customer.dto.chat.in.Inbound;
import com.woyao.customer.dto.chat.in.OrderRequestDTO;
import com.woyao.customer.service.IChatService;

@Component("unifiedMsgHandler")
public class UnifiedMsgHandler implements MsgHandler<Inbound>, InitializingBean {

	private Map<Class<?>, MsgHandler<Inbound>> handlerMap = new ConcurrentHashMap<>();

	@Resource(name = "inChatMsgHandler")
	private MsgHandler<Inbound> inChatMsgHandler;

	@Resource(name = "inChatMsgBlockHandler")
	private MsgHandler<Inbound> inChatMsgBlockHandler;

	@Resource(name = "orderRequestMsgHandler")
	private MsgHandler<Inbound> orderRequestMsgHandler;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Override
	public void afterPropertiesSet() throws Exception {
		handlerMap.put(ChatMsgDTO.class, inChatMsgHandler);
		handlerMap.put(ChatMsgBlockDTO.class, inChatMsgBlockHandler);
		handlerMap.put(OrderRequestDTO.class, orderRequestMsgHandler);
	}

	@Override
	public void handle(WebSocketSession wsSession, Inbound inbound) {
		MsgHandler<Inbound> handler = this.handlerMap.get(inbound.getClass());
		if (handler == null) {
			chatService.sendErrorMsg("不可处理的消息类型！" + inbound.getClass().getName(), wsSession);
			return;
		}
		handler.handle(wsSession, inbound);
	}

}
