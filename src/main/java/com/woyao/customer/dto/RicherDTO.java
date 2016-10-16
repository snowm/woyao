package com.woyao.customer.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RicherDTO implements Serializable {

	private static final long serialVersionUID = 2260608506954249912L;

	private ProfileDTO chatterDTO;

	private int payMsgCount;

	public ProfileDTO getChatterDTO() {
		return chatterDTO;
	}

	public void setChatterDTO(ProfileDTO chatterDTO) {
		this.chatterDTO = chatterDTO;
	}

	public int getPayMsgCount() {
		return payMsgCount;
	}

	public void setPayMsgCount(int payMsgCount) {
		this.payMsgCount = payMsgCount;
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
