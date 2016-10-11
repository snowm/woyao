package com.woyao.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonUtils {
	
	public static ObjectMapper om = new ObjectMapper();

	public static <T> String toString(T obj) throws JsonProcessingException {
		return om.writeValueAsString(obj);
	}

	public static <T> T toObject(String content, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		return om.readValue(content, clazz);
	}
}
