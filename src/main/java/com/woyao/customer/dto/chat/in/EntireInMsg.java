package com.woyao.customer.dto.chat.in;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.woyao.customer.dto.chat.BlockDTO;

public class EntireInMsg extends BaseChatMsgDTO {

	private Long productId;

	private List<BlockDTO> blocks = new ArrayList<>();

	private int blockSize;

	private String text;

	private String pic;

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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
