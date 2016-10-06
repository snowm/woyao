package com.woyao.customer.chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.woyao.customer.chat.dto.ErrorOutbound;
import com.woyao.customer.chat.dto.InMsgDTO;
import com.woyao.customer.chat.dto.Inbound;
import com.woyao.customer.chat.dto.OutMsgDTO;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.security.SharedSessionContext;

public class ChatWebSocketHandler extends AbstractWebSocketHandler {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "chatService")
	private IChatService chatService;

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		this.chatService.leave(session);
		log.debug("connectionClosed:" + session.getId());
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		ByteBuffer buffer = message.getPayload();
		String path = ChatUtils.savePic(buffer);
		String picUrl = "/pic/" + path;
		log.info(picUrl);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		String payload = message.getPayload();
		if (StringUtils.isBlank(payload)) {
			return;
		}
		Inbound request = Inbound.parse(payload);
		try {
			if (request instanceof InMsgDTO) {
				InMsgDTO msg = (InMsgDTO) request;
				long msgId = this.saveMsg(msg);

				OutMsgDTO outMsg = new OutMsgDTO();
				outMsg.setId(msgId);
				outMsg.setText(msg.getText());
				outMsg.setTo(msg.getTo());
				Long chatterId = WebsocketSessionHttpSessionContainer.getChatterId(session);
				ChatterDTO sender = this.chatService.getChatter(chatterId);
				outMsg.setSender(sender);

				this.send(session, outMsg);
			}
		} catch (RuntimeException | Error ex) {
			new ErrorOutbound(ex.getMessage()).send(session);
		}
	}

	private long saveMsg(InMsgDTO msg) {
		// TODO
		return this.getMsgId();
	}

	private AtomicLong msgIdGenerator = new AtomicLong();

	private long getMsgId() {
		return msgIdGenerator.getAndIncrement();
	}

	private void send(WebSocketSession session, OutMsgDTO outMsg) throws IOException {
		Set<WebSocketSession> toSessions = this.getTargetSessions(session, outMsg);
		for (WebSocketSession toSession : toSessions) {
			if (toSession != null) {
				outMsg.send(toSession);
			}
		}
	}

	private Set<WebSocketSession> getTargetSessions(WebSocketSession session, OutMsgDTO outMsg) {
		Long chatRoomId = WebsocketSessionHttpSessionContainer.getChatRoomId(session);
		Long chatterId = outMsg.getTo();
		if (chatterId != null) {
			return this.chatService.getTargetChatterSessions(outMsg.getTo());
		}
		if (chatRoomId != null) {
			return this.chatService.getAllRoomChatterSessions(chatRoomId);
		}
		throw new RuntimeException();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		try{
		String httpSessionId = (String) session.getAttributes().get(WebsocketSessionHttpSessionContainer.SESSION_ATTR_HTTPSESSION_ID);
		HttpSession httpSession = SharedSessionContext.getSession(httpSessionId);
		chatService.newChatter(session, httpSession);

		if (log.isDebugEnabled()) {
			log.debug(String.format("connectionEstablished, wsSessionId: %s, httpSessionId: %s", session.getId(), httpSession.getId()));
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
