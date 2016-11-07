package com.woyao.customer.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.snowm.security.profile.domain.Gender;
import com.woyao.PaginationQueryRequestDTO;

public class ChatterQueryRequest extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 1L;

	private Gender gender;

	private RicherType richerType;

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public RicherType getRicherType() {
		return richerType;
	}

	public void setRicherType(RicherType richerType) {
		this.richerType = richerType;
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
