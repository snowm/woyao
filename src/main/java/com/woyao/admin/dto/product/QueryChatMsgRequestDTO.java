package com.woyao.admin.dto.product;

import com.woyao.PaginationQueryRequestDTO;

public class QueryChatMsgRequestDTO extends PaginationQueryRequestDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long from;
	
	private String fromName;
	
	private Long to;
	
	private String toName;
	
	private boolean free = true;
	
	private Long productName;

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public Long getProductName() {
		return productName;
	}

	public void setProductName(Long productName) {
		this.productName = productName;
	}
	

	
	

}
