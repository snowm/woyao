package com.woyao.customer.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import com.snowm.utils.query.PaginationBean;
import com.woyao.customer.chat.WebsocketSessionHttpSessionContainer;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.ChatterQueryRequestDTO;
import com.woyao.customer.service.IChatService;
import com.woyao.dao.CommonDao;

@Component("chatService")
public class ChatServiceImpl implements IChatService {

	@Resource(name = "websocketSessionHttpSessionContainer")
	private WebsocketSessionHttpSessionContainer container;

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(readOnly = true)
	@Override
	public String newChatter(WebSocketSession wsSession, HttpSession httpSession) {
		this.container.wsEnabled(wsSession, httpSession);
		return null;
	}

	@Override
	public void leave(WebSocketSession wsSession) {
		this.container.wsClosed(wsSession.getId());
	}

	@Override
	public ChatterDTO getChatter(long chatterId) {
		return null;
	}

	@Override
	public List<ChatterDTO> listOnlineChatters() {
		return null;
	}

	@Override
	public Set<WebSocketSession> getTargetChatterSessions(long chatterId) {
		return this.container.getWsSessionOfChatter(chatterId);
	}

	@Override
	public Set<WebSocketSession> getAllRoomChatterSessions(long chatRoomId) {
		return this.container.getWsSessionOfRoom(chatRoomId);
	}

}
