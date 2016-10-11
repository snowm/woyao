package com.woyao.admin.dto.product;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.admin.dto.DTOConfig;

public class QueryChatMsgRequestDTO extends PaginationQueryRequestDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long from;
	
	private String fromName;
	
	private Long to;
	
	private String toName;
	
	private Boolean free;
	
	private Long productName;
	
	private Boolean deleted = null;
	
	private Integer shopId;
	
	private Long chatRoomId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date startcreationDate;//开始创建时间

	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date endcreationDate; //最后创建时间
	
	public Date getStartcreationDate() {
		return startcreationDate;
	}

	public void setStartcreationDate(Date startcreationDate) {
		this.startcreationDate = startcreationDate;
	}

	public Date getEndcreationDate() {
		return endcreationDate;
	}

	public void setEndcreationDate(Date endcreationDate) {
		this.endcreationDate = endcreationDate;
	}

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

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

	

	public Boolean getFree() {
		return free;
	}

	public void setFree(Boolean free) {
		this.free = free;
	}

	public Long getProductName() {
		return productName;
	}

	public void setProductName(Long productName) {
		this.productName = productName;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
