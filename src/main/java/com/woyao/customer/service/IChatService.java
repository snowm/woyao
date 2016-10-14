package com.woyao.customer.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.web.socket.WebSocketSession;

import com.snowm.security.profile.domain.Gender;
import com.snowm.utils.query.PaginationBean;
import com.woyao.customer.dto.ChatPicDTO;
import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.chat.MsgQueryRequest;
import com.woyao.customer.dto.chat.in.InMsg;
import com.woyao.customer.dto.chat.out.OutMsgDTO;
import com.woyao.customer.dto.chat.out.Outbound;

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

	void acceptMsg(WebSocketSession wsSession, InMsg inMsg);

	void sendRoomMsg(Outbound outbound, Long chatRoomId, WebSocketSession wsSession);

	void sendPrivacyMsg(Outbound outbound, Long to, WebSocketSession wsSession);

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
	PaginationBean<ChatterDTO> listOnlineChatters(Long selfChatterId, long chatRoomId, Gender gender, long pageNumber, int pageSize);
	
	/**
	 * 获取历史消息
	 * @param request
	 * @return
	 */
	List<OutMsgDTO> listHistoryMsg(MsgQueryRequest request);

	ChatterDTO getChatterFromDB(long chatterId);
	
	List<ChatPicDTO> getPicUrl(Long id,Long pageNumber,Integer pageSize);
	
	void sendErrorMsg(String reason, WebSocketSession wsSession);
}
