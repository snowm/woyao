package com.woyao.customer;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woyao.customer.dto.chat.out.OutMsgDTO;
import com.woyao.utils.JsonUtils;

public class TestJson {

	@Test
	public void testTransientProperty() throws JsonProcessingException {
		OutMsgDTO dto = new OutMsgDTO();
		String rs = JsonUtils.toString(dto);
		System.out.println(rs);
	}
}
