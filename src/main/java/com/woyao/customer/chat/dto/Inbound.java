package com.woyao.customer.chat.dto;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.woyao.JsonUtils;

public abstract class Inbound {

	private static Log log = LogFactory.getLog(Inbound.class);

	public static Inbound parse(String payload) {
		int index = payload.indexOf('\n');
		String type, data;
		if (index == -1) {
			type = payload.trim();
			data = "";
		} else {
			type = payload.substring(0, index).trim();
			data = payload.substring(index + 1);
		}
		try {
			switch (type) {
			case "msg":
				return JsonUtils.toObject(data, InMsgDTO.class);
			case "msgBlock":
				return JsonUtils.toObject(data, InMsgBlockDTO.class);
			default:
				return null;
			}
		} catch (IOException e) {
			log.warn("Parse in message error:" + payload, e);
			return null;
		}

//		try {
//			InMsgDTO msg = om.readValue(payload, InMsgDTO.class);
//			String base64PicString = msg.getPic();
//			if (!StringUtils.isBlank(base64PicString)) {
//				String path = File.separator + "pic" + File.separator + ChatUtils.savePic(base64PicString);
//				log.info("saved pic:" + path);
//				msg.setPic(path);
//			}
//			return msg;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
	}
}
