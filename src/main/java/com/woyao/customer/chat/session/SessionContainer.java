package com.woyao.customer.chat.session;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.woyao.customer.chat.SessionUtils;
import com.woyao.customer.dto.ChatRoomStatistics;
import com.woyao.customer.dto.ProfileDTO;

@Component("sessionContainer")
public class SessionContainer {

	public static final String SESSION_ATTR_HTTPSESSION_ID = HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME;
	public static final String SESSION_ATTR_HTTPSESSION = "WOYAO.HTTP.SESSION";
	public static final String SESSION_ATTR_REMOTE_IP = "WOYAO.REMOTE.IP";
	public static final String SESSION_ATTR_CHATTER = "WOYAO.CHATTER";
	public static final String SESSION_ATTR_SHOP_ID = "WOYAO.SHOP.ID";
	public static final String SESSION_ATTR_CHATROOM_ID = "WOYAO.CHATROOM.ID";
	public static final String SESSION_ATTR_MSG_CACHE_LOCK = "WOYAO.MSG.CACHE.LOCK";
	public static final String SESSION_ATTR_MSG_CACHE = "WOYAO.MSG.CACHE";
	public static final String SESSION_ATTR_ISDAPIN = "WOYAO.IS.DAPIN";

	/**
	 * <pre>
	 * 保存所有连入聊天室的wsSession, 包括大屏session
	 * websocketSession id为key，
	 * websocketSession 为value
	 * </pre>
	 */
	private Map<String, WebSocketSession> wsSessionMap = new ConcurrentHashMap<>();

	/**
	 * <pre>
	 * 保存所有聊天者的id到websocketSession（可有多个）的映射，包括大屏session
	 * profile的id为key，(大屏的id比较特别，是负数: = -10000 - shop.id)
	 * websocketSession ids 为value
	 * </pre>
	 */
	private Map<Long, Set<String>> clientWsSessionMap = new ConcurrentHashMap<>();

	/**
	 * 保存各个聊天室的信息，roomInfo里面包含对应的大屏session，chatter session，以及房间统计信息
	 */
	private Map<Long, RoomInfo> roomInfoMap = new ConcurrentHashMap<>();

	/**
	 * 保存聊天者的id到其对应的操作锁的映射
	 */
	private Map<Long, ReentrantReadWriteLock> chatterLockMap = new ConcurrentHashMap<>();

	/**
	 * 保存聊天室的id到其对应的操作锁的映射
	 */
	private Map<Long, ReentrantReadWriteLock> chatRoomLockMap = new ConcurrentHashMap<>();

	public void wsEnabled(WebSocketSession wsSession) {
		String wsSessionId = wsSession.getId();
		this.wsSessionMap.put(wsSessionId, wsSession);

		// 加入chatter session映射
		Long chatterId = SessionUtils.getChatterId(wsSession);
		Set<String> wsSessionIds = this.clientWsSessionMap.computeIfAbsent(chatterId, k -> new HashSet<>());
		ReentrantReadWriteLock chatterLk = this.getChatterLock(chatterId);
		try {
			chatterLk.writeLock().lock();
			wsSessionIds.add(wsSessionId);
		} finally {
			chatterLk.writeLock().unlock();
		}

		// 加入room session映射，更新room统计信息
		Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
		Long shopId = SessionUtils.getShopId(wsSession);
		RoomInfo roomInfo = this.roomInfoMap.computeIfAbsent(chatRoomId, k -> {
			RoomInfo inf = new RoomInfo();
			inf.setId(chatRoomId);
			inf.setShopId(shopId);
			inf.getStatistics().setId(chatRoomId);
			inf.getStatistics().setShopId(shopId);
			return inf;
		});

		ReentrantReadWriteLock roomLk = this.getChatRoomLock(chatRoomId);
		try {
			roomLk.writeLock().lock();
			if (SessionUtils.isDapin(wsSession)) {
				roomInfo.addDapin(wsSessionId);
			} else {
				roomInfo.addChatter(wsSessionId);
				roomInfo.calcStatistics();
			}
		} finally {
			roomLk.writeLock().unlock();
		}
	}

	public void wsClosed(WebSocketSession wsSession) {
		String sessionId = wsSession.getId();
		this.removeChatterSession(wsSession);
		this.removeChatterFromRoom(wsSession);

		this.wsSessionMap.remove(sessionId);
	}

	/**
	 * 移除room session列表中对应的wsSession
	 * 
	 * @param wsSession
	 */
	private void removeChatterFromRoom(WebSocketSession wsSession) {
		Long chatRoomId = SessionUtils.getChatRoomId(wsSession);
		RoomInfo roomInfo = this.roomInfoMap.get(chatRoomId);
		if (roomInfo == null) {
			return;
		}
		ReentrantReadWriteLock lock = this.getChatRoomLock(chatRoomId);

		String sessionId = wsSession.getId();
		try {
			lock.writeLock().lock();
			if (removeEmptyRoomInfo(roomInfo)) {
				return;
			}
			if (SessionUtils.isDapin(wsSession)) {
				roomInfo.removeDapin(sessionId);
			} else {
				roomInfo.removeChatter(sessionId);
				roomInfo.calcStatistics();
			}
			removeEmptyRoomInfo(roomInfo);
		} finally {
			lock.writeLock().unlock();
		}
	}

	private boolean removeEmptyRoomInfo(RoomInfo roomInfo) {
		if (roomInfo.isEmpty()) {
			Long chatRoomId = roomInfo.getId();
			this.roomInfoMap.remove(chatRoomId);
			this.removeChatRoomLock(chatRoomId);
			return true;
		}
		return false;
	}

	/**
	 * 移除chatter session列表中对应的wsSession
	 * 
	 * @param wsSession
	 */
	private void removeChatterSession(WebSocketSession wsSession) {
		Long chatterId = SessionUtils.getChatterId(wsSession);
		Set<String> wsSessionIds = this.clientWsSessionMap.get(chatterId);

		ReentrantReadWriteLock lock = this.getChatterLock(chatterId);
		try {
			lock.writeLock().lock();
			if (removeEmptyChatterSessionList(wsSessionIds, chatterId)) {
				return;
			}
			String sessionId = wsSession.getId();
			wsSessionIds.remove(sessionId);
			removeEmptyChatterSessionList(wsSessionIds, chatterId);
		} finally {
			lock.writeLock().unlock();
		}
	}

	private boolean removeEmptyChatterSessionList(Set<String> wsSessionIds, Long chatterId) {
		if (CollectionUtils.isEmpty(wsSessionIds)) {
			this.clientWsSessionMap.remove(chatterId);
			this.removeChatterLock(chatterId);
			return true;
		}
		return false;
	}

	public ProfileDTO getChatter(long chatterId) {
		Set<String> sessionIds = this.clientWsSessionMap.get(chatterId);
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
		Set<String> sessionIds = this.clientWsSessionMap.get(chatterId);
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
	
	public WebSocketSession getSession(String sessionId) {
		return this.wsSessionMap.get(sessionId);
	}

	public Set<WebSocketSession> getWsSessionOfRoom(long chatRoomId) {
		Set<WebSocketSession> rs = new HashSet<>();
		RoomInfo roomInfo = this.roomInfoMap.get(chatRoomId);
		if (roomInfo == null || roomInfo.isEmpty()) {
			return rs;
		}
		Set<String> sessionIds = roomInfo.getAllSessionIds();
		ReentrantReadWriteLock lock = this.getChatRoomLock(chatRoomId);
		try {
			lock.readLock().lock();
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

	public Set<WebSocketSession> getChatterWsSessionOfRoom(long chatRoomId) {
		Set<WebSocketSession> rs = new HashSet<>();
		RoomInfo roomInfo = this.roomInfoMap.get(chatRoomId);
		if (roomInfo == null || roomInfo.isEmpty()) {
			return rs;
		}
		Set<String> sessionIds = roomInfo.getChatterWsSessionIds();
		ReentrantReadWriteLock lock = this.getChatRoomLock(chatRoomId);
		try {
			lock.readLock().lock();
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
		RoomInfo roomInfo = this.roomInfoMap.get(chatRoomId);
		if (roomInfo == null) {
			return null;
		}
		return roomInfo.getStatistics();
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
