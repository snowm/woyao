package com.woyao.customer.dto.chat;

public class MsgQueryRequest {

	private Long minId;
	private Long maxId;
	private int pageSize;

	private Long shopId;
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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
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

}
