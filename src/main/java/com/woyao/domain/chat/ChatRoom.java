package com.woyao.domain.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.woyao.domain.Shop;

@Entity
@Table(name = "CHAT_ROOM")
@TableGenerator(name = "chatRoomGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "chatRoom", allocationSize = 1, initialValue = 0)
public class ChatRoom extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "chatRoomGenerator")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "SHOP_ID", nullable = false)
	private Shop shop;

	@Column(name = "NAME")
	private String name;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
