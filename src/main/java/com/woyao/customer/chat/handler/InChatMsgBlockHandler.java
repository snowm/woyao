package com.woyao.customer.chat.handler;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.chat.MessageCacheOperator;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.chat.in.EntireInMsg;
import com.woyao.customer.dto.chat.in.ChatMsgBlockDTO;
import com.woyao.customer.service.IChatService;

@Component("inChatMsgBlockHandler")
public class InChatMsgBlockHandler implements MsgHandler<ChatMsgBlockDTO> {

	@Resource(name = "messageCacheOperator")
	private MessageCacheOperator messageCacheOperator;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Override
	public void handle(WebSocketSession wsSession, ChatMsgBlockDTO inbound) {
		Lock lock = SessionUtils.getMsgCacheLock(wsSession);
		Map<Long, EntireInMsg> cache = SessionUtils.getMsgCache(wsSession);
		EntireInMsg inMsg = this.messageCacheOperator.receiveMsg(lock, cache, inbound);
		if (inMsg == null) {
			return;
		}
		this.chatService.acceptMsg(wsSession, inMsg);
	}

}
