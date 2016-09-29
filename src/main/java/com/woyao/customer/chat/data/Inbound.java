package com.woyao.customer.chat.data;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Inbound {

	public static ObjectMapper om = new ObjectMapper();

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
			case "broadcast":
				return om.readValue(data, BroadcastInbound.class);
			case "sending":
				return om.readValue(data, SendingInbound.class);
			default:
				throw new IllegalArgumentException("Illegal type: " + type);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
