package com.woyao.customer.dto.chat;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MsgQueryRequest {

	private Long minId;
	private Long maxId;
	private Integer pageSize = 20;

	private Long chatRoomId;
	private Long selfChatterId;
	private Long withChatterId;

	public Long getMinId() {
		return minId;
	}

	public void setMinId(Long minId) {
		this.minId = minId;
	}

	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize == null || pageSize < 0) {
			this.pageSize = 20;
		} else {
			this.pageSize = pageSize;
		}
	}

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public Long getSelfChatterId() {
		return selfChatterId;
	}

	public void setSelfChatterId(Long selfChatterId) {
		this.selfChatterId = selfChatterId;
	}

	public Long getWithChatterId() {
		return withChatterId;
	}

	public void setWithChatterId(Long withChatterId) {
		this.withChatterId = withChatterId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
