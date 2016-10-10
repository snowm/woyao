package com.woyao.customer;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woyao.JsonUtils;
import com.woyao.customer.dto.chat.OutMsgDTO;

public class TestJson {

	@Test
	public void testTransientProperty() throws JsonProcessingException {
		OutMsgDTO dto = new OutMsgDTO();
		String rs = JsonUtils.toString(dto);
		System.out.println(rs);
	}
}
