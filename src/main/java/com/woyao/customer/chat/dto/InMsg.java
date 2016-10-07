package com.woyao.customer.chat.dto;

import java.util.ArrayList;
import java.util.List;

public class InMsg extends Inbound {

	private Long msgId;

	private Long to;

	private Long productId;

	private List<BlockDTO> blocks = new ArrayList<>();

	private int blockSize;

	private String text;

	private String pic;

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<BlockDTO> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<BlockDTO> blocks) {
		this.blocks = blocks;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

}
