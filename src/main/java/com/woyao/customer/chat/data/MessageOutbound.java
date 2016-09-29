package com.woyao.customer.chat.data;

import com.woyao.customer.dto.MsgDTO;

public class MessageOutbound extends Outbound {

	public MessageOutbound(MsgDTO msg) {
		super();
		this.msg = msg;
	}

	public MsgDTO getMsg() {
		return msg;
	}

}
