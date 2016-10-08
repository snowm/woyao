package com.woyao.customer.service;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.web.socket.WebSocketSession;

import com.snowm.utils.query.PaginationBean;
import com.woyao.customer.chat.dto.Inbound;
import com.woyao.customer.chat.dto.Outbound;
import com.woyao.customer.dto.ChatterDTO;

public interface IChatService {

	/**
	 * 新聊天者加入
	 * 
	 * @param wsSession
	 *            websocket session
	 * @param httpSession
	 *            http session
	 * @return
	 */
	String newChatter(WebSocketSession wsSession, HttpSession httpSession);

	/**
	 * 聊天者离开
	 * 
	 * @param wsSession
	 */
	void leave(WebSocketSession wsSession);

	void acceptMsg(WebSocketSession wsSession, Inbound inbound);

	void sendOutMsg(Outbound outbound, Long to, Long chatRoomId);

	Set<WebSocketSession> getTargetChatterSessions(long chatterId);

	Set<WebSocketSession> getAllRoomChatterSessions(long chatRoomId);

	/**
	 * 获取聊天者信息
	 * 
	 * @param chatterId
	 * @return
	 */
	ChatterDTO getChatter(long chatterId);

	/**
	 * 查询在线聊天者
	 * 
	 * @return
	 */
	PaginationBean<ChatterDTO> listOnlineChatters(long chatRoomId, long pageNumber, int pageSize);
	
}
