package com.woyao.customer.chat.dto;

/**
 * 样例：
 * 
 * <pre>
 * {
 *    from:123, //谁发的
 *    to:123, //发给谁
 *    text:'你好',
 *    pic:'98774857abcf376d',
 *    productId:1001,
 * }
 * </pre>
 * 
 * 后端会做消息最大限制，一条消息，图片只能有一个，文字内容+图片链接的长度不能超过500
 * 
 * @author jyang4
 *
 */
public class InMsgDTO extends Inbound {

	private Long to;

	private String text;

	private Long productId;

	private boolean includePic = false;

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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public boolean isIncludePic() {
		return includePic;
	}

	public void setIncludePic(boolean includePic) {
		this.includePic = includePic;
	}
	

}
