package com.woyao.customer.dto;

import java.io.Serializable;

public class RicherDTO implements Serializable {

	private static final long serialVersionUID = 2260608506954249912L;

	private ChatterDTO chatterDTO;

	private int payMsgCount;

	public ChatterDTO getChatterDTO() {
		return chatterDTO;
	}

	public void setChatterDTO(ChatterDTO chatterDTO) {
		this.chatterDTO = chatterDTO;
	}

	public int getPayMsgCount() {
		return payMsgCount;
	}

	public void setPayMsgCount(int payMsgCount) {
		this.payMsgCount = payMsgCount;
	}

}
