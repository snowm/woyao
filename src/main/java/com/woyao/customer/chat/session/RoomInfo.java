package com.woyao.customer.chat.session;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.woyao.customer.dto.ChatRoomStatistics;

public class RoomInfo {

	private long id;
	private long shopId;
	private ChatRoomStatistics statistics = new ChatRoomStatistics();
	private Set<String> chatterWsSessionIds = new HashSet<>();
	private Set<String> dapingWsSessionIds = new HashSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public ChatRoomStatistics getStatistics() {
		return statistics;
	}

	public Set<String> getChatterWsSessionIds() {
		return Collections.unmodifiableSet(chatterWsSessionIds);
	}

	public Set<String> getDapingWsSessionIds() {
		return Collections.unmodifiableSet(dapingWsSessionIds);
	}

	public boolean isEmpty() {
		return CollectionUtils.isEmpty(this.chatterWsSessionIds) && CollectionUtils.isEmpty(this.dapingWsSessionIds);
	}

	public void addChatter(String wsSessionId) {
		this.chatterWsSessionIds.add(wsSessionId);
	}

	public void removeChatter(String wsSessionId) {
		this.chatterWsSessionIds.remove(wsSessionId);
	}

	public void addDapin(String wsSessionId) {
		this.dapingWsSessionIds.add(wsSessionId);
	}

	public void removeDapin(String wsSessionId) {
		this.dapingWsSessionIds.remove(wsSessionId);
	}

	public Set<String> getAllSessionIds() {
		Set<String> sessionIds = new HashSet<>();
		sessionIds.addAll(this.getChatterWsSessionIds());
		sessionIds.addAll(this.getDapingWsSessionIds());
		return sessionIds;
	}

	public void calcStatistics() {
		statistics.setOnlineChattersNumber(chatterWsSessionIds.size());
	}

}
