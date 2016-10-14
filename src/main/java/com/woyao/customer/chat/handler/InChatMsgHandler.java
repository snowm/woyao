package com.woyao.customer.chat.handler;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.chat.MessageCacheOperator;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.chat.in.InMsg;
import com.woyao.customer.dto.chat.in.InMsgDTO;
import com.woyao.customer.service.IChatService;

@Component("inChatMsgHandler")
public class InChatMsgHandler implements MsgHandler<InMsgDTO> {

	@Resource(name = "messageCacheOperator")
	private MessageCacheOperator messageCacheOperator;

	@Resource(name = "chatService")
	private IChatService chatService;

	@Override
	public void handle(WebSocketSession wsSession, InMsgDTO inbound) {
		Long senderId = SessionUtils.getChatterId(wsSession);
		if (senderId.equals(inbound.getTo())) {
			this.chatService.sendErrorMsg("不能给自己发消息！", wsSession);
			return;
		}

		Lock lock = SessionUtils.getMsgCacheLock(wsSession);
		Map<Long, InMsg> cache = SessionUtils.getMsgCache(wsSession);
		InMsg inMsg = this.messageCacheOperator.receiveMsg(lock, cache, inbound);
		if (inMsg == null) {
			return;
		}

		this.chatService.acceptMsg(wsSession, inMsg);
	}

}
