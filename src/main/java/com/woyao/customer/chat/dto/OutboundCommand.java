package com.woyao.customer.chat.dto;

public interface OutboundCommand {

	/**
	 * 发送消息
	 */
	String SEND_MSG = "sm";
	
	/**
	 * 用户发送消息后收到的返回消息，用以标明这是自己发出的消息返回
	 */
	String SEND_MSG_ACK = "smACK";
	
	/**
	 * 删除消息
	 */
	String DEL_MSG = "dm";
}
