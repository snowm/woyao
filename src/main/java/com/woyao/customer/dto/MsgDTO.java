package com.woyao.customer.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 样例：
 * 
 * <pre>
 * {
 *    to:123, //发给谁
 *    contents: {
 *    	1:'你好！',
 *      2:'==img==23707zbd348099977fe',
 *      3:'怎么样？'
 *    } //内容有3块内容，第一块是文字'你好！'（当然，这个文字之中也可以有换行符）
 *        第二块是图片
 *        第三块又是文字
 * }
 * </pre>
 * 
 * 后端会做消息最大限制，一条消息，图片只能有一个，文字内容+图片链接的长度不能超过500
 * 
 * @author jyang4
 *
 */
public class MsgDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long to;

	private Map<Integer, String> contents;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public Map<Integer, String> getContents() {
		return contents;
	}

	public void setContents(Map<Integer, String> contents) {
		this.contents = contents;
	}

}
