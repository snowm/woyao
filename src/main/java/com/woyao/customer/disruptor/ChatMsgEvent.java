package com.woyao.customer.disruptor;

import java.io.Serializable;

import com.woyao.customer.dto.chat.out.Outbound;

public class ChatMsgEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String toSessionId;

	private Outbound outbound;

	public String getToSessionId() {
		return toSessionId;
	}

	public void setToSessionId(String toSessionId) {
		this.toSessionId = toSessionId;
	}

	public Outbound getOutbound() {
		return outbound;
	}

	public void setOutbound(Outbound outbound) {
		this.outbound = outbound;
	}

}
