package com.woyao.customer.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ChatRoomStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private Long shopId;

	private int onlineChattersNumber = 0;

	private ProfileDTO dailyRicher;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public int getOnlineChattersNumber() {
		return onlineChattersNumber;
	}

	public void setOnlineChattersNumber(int onlineChattersNumber) {
		this.onlineChattersNumber = onlineChattersNumber;
	}

	public ProfileDTO getDailyRicher() {
		return dailyRicher;
	}

	public void setDailyRicher(ProfileDTO dailyRicher) {
		this.dailyRicher = dailyRicher;
	}

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
