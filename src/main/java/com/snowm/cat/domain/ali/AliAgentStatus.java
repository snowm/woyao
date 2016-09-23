package com.snowm.cat.domain.ali;

import java.util.Map;
import java.util.TreeMap;

import com.snowm.hibernate.ext.EnumPersistedValueNotPresentException;
import com.snowm.hibernate.ext.usertype.PersistEnum;

public enum AliAgentStatus implements PersistEnum<AliAgentStatus> {

	UN_PLACED(1, "UN_PLACED"), PLACED(2, "PLACED");

	public final static String CLASS_NAME = "com.snowm.cat.domain.ali.AliAgentStatus";

	private int typeValue;

	private String desc;

	private final static Map<Integer, AliAgentStatus> cache = new TreeMap<>();

	static {
		for (AliAgentStatus e : AliAgentStatus.values()) {
			cache.put(e.getPersistedValue(), e);
		}
	}

	private AliAgentStatus(int typeValue, String desc) {
		this.typeValue = typeValue;
		this.desc = desc;
	}

	public int getTypeValue() {
		return typeValue;
	}

	public String getDesc() {
		return desc;
	}

	@Override
	public Integer getPersistedValue() {
		return getTypeValue();
	}

	public static AliAgentStatus getEnum(Integer persistedValue) {
		if (persistedValue == null) {
			return null;
		}
		AliAgentStatus e = cache.get(persistedValue);
		if (e == null) {
			throw new EnumPersistedValueNotPresentException(persistedValue, AliAgentStatus.class);
		}
		return e;
	}

	public static Map<Integer, AliAgentStatus> getAllValueMap() {
		return java.util.Collections.unmodifiableMap(cache);
	}
}
