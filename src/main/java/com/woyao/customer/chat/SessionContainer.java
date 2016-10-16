package com.woyao.customer.chat;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.woyao.customer.dto.ChatRoomStatistics;
import com.woyao.customer.dto.ProfileDTO;

@Component("sessionContainer")
public class SessionContainer {

	public static final String SESSION_ATTR_HTTPSESSION_ID = HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME;
	public static final String SESSION_ATTR_REMOTE_IP = "REMOTE_IP";
	public static final String SESSION_ATTR_CHATTER = "CHATTER";
	public static final String SESSION_ATTR_SHOP_ID = "SHOP_ID";
	public static final String SESSION_ATTR_CHATROOM_ID = "CHATROOM_ID";
	public static final String SESSION_ATTR_MSG_CACHE_LOCK = "MSG_CACHE_LOCK";
	public static final String SESSION_ATTR_MSG_CACHE = "MSG_CACHE";

	/**
	 * 保存webSocket到http session的映射，websocketSession id为key， httpSession id
	 * 为value
	 */
	private Map<String, String> wsHttpSessionMap = new ConcurrentHashMap<>();

	/**
	 * websocketSession id为key， websocketSession 为value
	 */
	private Map<String, WebSocketSession> wsSessionMap = new ConcurrentHashMap<>();

	/**
	 * chatRoom id为key， websocketSession ids 为value
	 */
	private Map<Long, Set<String>> chatRoomWsSessionMap = new ConcurrentHashMap<>();

	/**
	 * chatRoom id为key， ChatRoomStatistics 为value
	 */
	private Map<Long, ChatRoomStatistics> chatRoomStatisticsMap = new ConcurrentHashMap<>();

	/**
	 * 保存聊天者的id到websocketSession（可有多个）的映射，chatter id为key， websocketSession ids
	 * 为value
	 */
	private Map<Long, Set<String>> chatterWsSessionMap = new ConcurrentHashMap<>();

	/**
	 * 保存聊天者的id到其对应的操作锁的映射
	 */
	private Map<Long, ReentrantReadWriteLock> chatterLockMap = new ConcurrentHashMap<>();

	/**
	 * 保存聊天室的id到其对应的操作锁的映射
	 */
	private Map<Long, ReentrantReadWriteLock> chatRoomLockMap = new ConcurrentHashMap<>();

	public void wsEnabled(WebSocketSession wsSession, HttpSession httpSession) {
		String wsSessionId = wsSession.getId();
		this.wsHttpSessionMap.put(wsSessionId, httpSession.getId());
		this.wsSessionMap.put(wsSessionId, wsSession);

		// 加入chatter session映射
		Long chatterId = SessionUtils.getChatterId(wsSession);
		Set<String> wsSessionIds = this.chatterWsSessionMap.computeIfAbsent(chatterId, k -> new HashSet<>());
		ReentrantReadWriteLock chatterLk = this.getChatterLock(chatterId);
		try {
			chatterLk.writeLock().lock();
			wsSessionIds.add(wsSessionId);
		} finally {
			chatterLk.writeLock().unlock();
		}

		// 加入room session映射，更新room统计信息
		Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
		Set<String> roomWsSessionIds = this.chatRoomWsSessionMap.computeIfAbsent(chatRoomId, k -> new HashSet<>());
		ChatRoomStatistics roomStatistics = this.chatRoomStatisticsMap.computeIfAbsent(chatRoomId, k -> {
			ChatRoomStatistics st = new ChatRoomStatistics();
			st.setId(chatRoomId);
			st.setShopId(SessionUtils.getShopId(wsSession));
			st.setOnlineChattersNumber(roomWsSessionIds.size());
			return st;
		});
		ReentrantReadWriteLock roomLk = this.getChatRoomLock(chatRoomId);
		try {
			roomLk.writeLock().lock();
			roomWsSessionIds.add(wsSessionId);
			roomStatistics.setOnlineChattersNumber(roomWsSessionIds.size());
		} finally {
			roomLk.writeLock().unlock();
		}
	}

	public void wsClosed(String sessionId) {
		WebSocketSession wsSession = this.wsSessionMap.get(sessionId);
		this.removeChatterSession(sessionId, wsSession);
		this.removeChatterFromRoom(sessionId, wsSession);

		this.wsHttpSessionMap.remove(sessionId);
		this.wsSessionMap.remove(sessionId);
	}

	private void removeChatterFromRoom(String sessionId, WebSocketSession wsSession) {
		// 移除room session列表中对应的wsSession
		Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
		Set<String> wsSessionIds = this.chatRoomWsSessionMap.get(chatRoomId);
		ReentrantReadWriteLock lock = this.getChatRoomLock(chatRoomId);
		try {
			lock.readLock().lock();
			if (CollectionUtils.isEmpty(wsSessionIds)) {
				this.chatRoomWsSessionMap.remove(chatRoomId);
				this.removeChatRoomLock(chatRoomId);
				return;
			}
		} finally {
			lock.readLock().unlock();
		}

		ChatRoomStatistics roomStatistics = this.chatRoomStatisticsMap.get(chatRoomId);
		try {
			lock.writeLock().lock();
			wsSessionIds.remove(sessionId);
			roomStatistics.setOnlineChattersNumber(wsSessionIds.size());
			if (wsSessionIds.isEmpty()) {
				this.chatRoomWsSessionMap.remove(chatRoomId);
				this.removeChatRoomLock(chatRoomId);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	private void removeChatterSession(String sessionId, WebSocketSession wsSession) {
		// 移除chatter session列表中对应的wsSession
		Long chatterId = SessionUtils.getChatterId(wsSession);
		Set<String> wsSessionIds = this.chatterWsSessionMap.get(chatterId);
		ReentrantReadWriteLock lock = this.getChatterLock(chatterId);
		try {
			lock.readLock().lock();
			if (CollectionUtils.isEmpty(wsSessionIds)) {
				this.chatterWsSessionMap.remove(chatterId);
				this.removeChatterLock(chatterId);
				return;
			}
		} finally {
			lock.readLock().unlock();
		}

		try {
			lock.writeLock().lock();
			wsSessionIds.remove(sessionId);
			if (wsSessionIds.isEmpty()) {
				this.chatterWsSessionMap.remove(chatterId);
				this.removeChatterLock(chatterId);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	public ProfileDTO getChatter(long chatterId) {
		Set<String> sessionIds = this.chatterWsSessionMap.get(chatterId);
		ReentrantReadWriteLock lock = this.getChatterLock(chatterId);
		try {
			lock.readLock().lock();
			if (!CollectionUtils.isEmpty(sessionIds)) {
				String sessionId = sessionIds.iterator().next();
				WebSocketSession wsSession = this.wsSessionMap.get(sessionId);
				return SessionUtils.getChatter(wsSession);
			}
		} finally {
			lock.readLock().unlock();
		}
		return null;
	}

	public Set<WebSocketSession> getWsSessionOfChatter(long chatterId) {
		Set<String> sessionIds = this.chatterWsSessionMap.get(chatterId);
		ReentrantReadWriteLock lock = this.getChatterLock(chatterId);
		try {
			lock.readLock().lock();
			Set<WebSocketSession> rs = new HashSet<>();
			if (sessionIds != null && !sessionIds.isEmpty()) {
				for (String sessionId : sessionIds) {
					rs.add(this.wsSessionMap.get(sessionId));
				}
			}
			return rs;
		} finally {
			lock.readLock().unlock();
		}
	}

	public Set<WebSocketSession> getWsSessionOfRoom(long chatRoomId) {
		Set<String> sessionIds = this.chatRoomWsSessionMap.get(chatRoomId);
		ReentrantReadWriteLock lock = this.getChatRoomLock(chatRoomId);
		try {
			lock.readLock().lock();
			Set<WebSocketSession> rs = new HashSet<>();
			if (sessionIds != null && !sessionIds.isEmpty()) {
				for (String sessionId : sessionIds) {
					rs.add(this.wsSessionMap.get(sessionId));
				}
			}
			return rs;
		} finally {
			lock.readLock().unlock();
		}
	}

	public ChatRoomStatistics getRoomStatistics(long chatRoomId) {
		return this.chatRoomStatisticsMap.get(chatRoomId);
	}
	
	/**
	 * Thread safe
	 * 
	 * @param chatterId
	 * @return
	 */
	private ReentrantReadWriteLock getChatterLock(Long chatterId) {
		return this.chatterLockMap.computeIfAbsent(chatterId, k -> new ReentrantReadWriteLock());
	}

	/**
	 * Thread safe
	 * 
	 * @param chatterId
	 */
	private void removeChatterLock(Long chatterId) {
		this.chatterLockMap.remove(chatterId);
	}

	/**
	 * Thread safe
	 * 
	 * @param chatRoomId
	 * @return
	 */
	private ReentrantReadWriteLock getChatRoomLock(Long chatRoomId) {
		return this.chatRoomLockMap.computeIfAbsent(chatRoomId, k -> new ReentrantReadWriteLock());
	}

	/**
	 * Thread safe
	 * 
	 * @param chatRoomId
	 */
	private void removeChatRoomLock(Long chatRoomId) {
		this.chatRoomLockMap.remove(chatRoomId);
	}
}
