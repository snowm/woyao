package com.woyao.customer.dto;

public class MsgProductDTO extends ProductDTO {

	private static final long serialVersionUID = 2260608506954249912L;

	private int holdTime;

	public int getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

}
