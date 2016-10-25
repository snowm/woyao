package com.woyao.utils;

import java.io.IOException;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonUtils {
	
	public static ObjectMapper om = new ObjectMapper();
	
	static {
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.setTimeZone(TimeZone.getDefault());
	}

	public static <T> String toString(T obj) throws JsonProcessingException {
		return om.writeValueAsString(obj);
	}

	public static <T> T toObject(String content, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		return om.readValue(content, clazz);
	}
}
