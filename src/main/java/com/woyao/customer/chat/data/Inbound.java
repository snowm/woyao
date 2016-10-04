package com.woyao.customer.chat.data;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woyao.customer.chat.ChatUtils;
import com.woyao.customer.dto.MsgDTO;

public abstract class Inbound {

	private static Log log = LogFactory.getLog(Inbound.class);

	public static ObjectMapper om = new ObjectMapper();

	public static Inbound parse(String payload) {
		log.info(payload);
		try {
			MsgDTO msg = om.readValue(payload, MsgDTO.class);
			String base64PicString = msg.getPic();
			String path = File.separator + "pic" + File.separator + ChatUtils.savePic(base64PicString.getBytes());
			msg.setPic(path);
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
