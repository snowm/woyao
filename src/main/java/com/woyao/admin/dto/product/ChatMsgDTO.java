package com.woyao.admin.dto.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woyao.admin.dto.BasePKDTO;
import com.woyao.admin.dto.DTOConfig;

public class ChatMsgDTO  extends BasePKDTO {

	private static final long serialVersionUID = 1L;
	
	private Long chatRoomId;
	
	private String chatRoomName;
	
	private Long from;
	
	private String fromName;
	
	private Long to;
	
	private String content;
	
	private boolean free = true;
	
	private Long productId;
	
	private String productName;
	
	private Integer productUnitPrice;
	
	private String text;//类容
	
	private String picURL;//图片
	
	private String orderId;//订单号
	
	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date startcreationDate;//开始创建时间

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date endcreationDate; //最后创建时间

	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
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

	public Integer getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(Integer productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	
	

}
