package com.woyao.customer.dto;

import java.io.Serializable;

import com.woyao.customer.chat.data.Inbound;

/**
 * 样例：
 * 
 * <pre>
 * {
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
public class MsgDTO extends Inbound implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String to;

	private String text;

	private String pic;

	private Long productId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
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

}
