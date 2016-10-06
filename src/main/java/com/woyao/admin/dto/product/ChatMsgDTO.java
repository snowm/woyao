package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class ChatMsgDTO  extends BasePKDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long chatRoomId;
	
	private Long chatRoomName;
	
	private Long from;
	
	private Long to;
	
	private String content;
	
	private boolean free = true;
	
	private Long productId;
	
	private String productName;

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public Long getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(Long chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	

}
