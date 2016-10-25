package com.woyao.customer.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MsgProductDTO extends ProductDTO {

	private static final long serialVersionUID = 2260608506954249912L;

	private int holdTime;
	
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
