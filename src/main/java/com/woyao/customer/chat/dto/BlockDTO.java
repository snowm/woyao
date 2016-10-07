package com.woyao.customer.chat.dto;

public class BlockDTO extends Inbound {

	private String block;

	private int seq;

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}
