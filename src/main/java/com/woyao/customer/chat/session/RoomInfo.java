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
	private Set<String> allWsSessionIds = new HashSet<>();

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
		return CollectionUtils.isEmpty(allWsSessionIds);
	}

	public void addChatter(String wsSessionId) {
		this.chatterWsSessionIds.add(wsSessionId);
		this.allWsSessionIds.add(wsSessionId);
	}

	public void removeChatter(String wsSessionId) {
		this.chatterWsSessionIds.remove(wsSessionId);
		this.allWsSessionIds.remove(wsSessionId);
	}

	public void addDapin(String wsSessionId) {
		this.dapingWsSessionIds.add(wsSessionId);
		this.allWsSessionIds.add(wsSessionId);
	}

	public void removeDapin(String wsSessionId) {
		this.dapingWsSessionIds.remove(wsSessionId);
		this.allWsSessionIds.remove(wsSessionId);
	}

	public Set<String> getAllSessionIds() {
		return Collections.unmodifiableSet(this.allWsSessionIds);
	}

	public void calcStatistics() {
		statistics.setOnlineChattersNumber(chatterWsSessionIds.size());
	}

}
