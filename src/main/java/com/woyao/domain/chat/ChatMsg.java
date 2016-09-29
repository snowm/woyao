package com.woyao.domain.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Table(name = "CHAT_MSG")
@TableGenerator(name = "chatMsgGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "chatMsg", allocationSize = 1, initialValue = 0)
public class ChatMsg extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "chatMsgGenerator")
	private Long id;

	@Column(name = "ROOM_ID")
	private Long chatRoomId;

	@Column(name = "PROFILE_WX_FROM", nullable = false)
	private Long from;

	@Column(name = "PROFILE_WX_TO")
	private Long to;

	@Column(name = "CONTENT", nullable = false, length = 500)
	private String content;

	/**
	 * 是否免费
	 */
	@Column(name = "FREE")
	private boolean free = true;

	/**
	 * 哪种付费消息
	 */
	@Column(name = "PRODUCT_ID")
	private Long productId;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
