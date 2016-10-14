package com.woyao.customer.dto.chat.out;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.woyao.customer.dto.PrepayInfoDTO;

public class PrepayMsgDTO extends Outbound {

	private Long clientMsgId;

	private PrepayInfoDTO prepayInfo;

	public Long getClientMsgId() {
		return clientMsgId;
	}

	public void setClientMsgId(Long clientMsgId) {
		this.clientMsgId = clientMsgId;
	}

	public PrepayInfoDTO getPrepayInfo() {
		return prepayInfo;
	}

	public void setPrepayInfo(PrepayInfoDTO prepayInfo) {
		this.prepayInfo = prepayInfo;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
