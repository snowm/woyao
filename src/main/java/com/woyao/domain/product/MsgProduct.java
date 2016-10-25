package com.woyao.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "MSG_PRODUCT")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "msgProduct")
public class MsgProduct extends Product {

	private static final long serialVersionUID = 1L;

	@Column(name = "HOLD_TIME", nullable = false)
	private int holdTime;

	@Column(name = "CODE", length = 20)
	private String effectCode;

	public int getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(int holdTime) {
		this.holdTime = holdTime;
	}

	public String getEffectCode() {
		return effectCode;
	}

	public void setEffectCode(String effectCode) {
		this.effectCode = effectCode;
	}

}
