package com.woyao.customer.chat.handler;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.chat.MessageCacheOperator;
import com.woyao.customer.chat.MsgErrorConstants;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.chat.in.EntireInMsg;
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
			this.chatService.sendErrorMsg(MsgErrorConstants.ERR_SEND_SELF, wsSession);
			return;
		}

		Lock lock = SessionUtils.getMsgCacheLock(wsSession);
		Map<Long, EntireInMsg> cache = SessionUtils.getMsgCache(wsSession);
		EntireInMsg inMsg = this.messageCacheOperator.receiveMsg(lock, cache, inbound);
		if (inMsg == null) {
			return;
		}

		this.chatService.acceptMsg(wsSession, inMsg);
	}

}
