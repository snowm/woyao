package com.snowm.cat.domain.ali;

import java.util.Map;
import java.util.TreeMap;

import com.snowm.hibernate.ext.EnumPersistedValueNotPresentException;
import com.snowm.hibernate.ext.usertype.PersistEnum;

public enum AliOrderStatus implements PersistEnum<AliOrderStatus> {

	CREATED(1, "CREATED"), DELIVERED(2, "DELIVERED"), FINISHED(3, "FINISHED");

	public final static String CLASS_NAME = "com.snowm.cat.domain.ali.AliOrderStatus";

	private int typeValue;

	private String desc;

	private final static Map<Integer, AliOrderStatus> cache = new TreeMap<>();

	static {
		for (AliOrderStatus e : AliOrderStatus.values()) {
			cache.put(e.getPersistedValue(), e);
		}
	}

	private AliOrderStatus(int typeValue, String desc) {
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

	public static AliOrderStatus getEnum(Integer persistedValue) {
		if (persistedValue == null) {
			return null;
		}
		AliOrderStatus e = cache.get(persistedValue);
		if (e == null) {
			throw new EnumPersistedValueNotPresentException(persistedValue, AliOrderStatus.class);
		}
		return e;
	}

	public static Map<Integer, AliOrderStatus> getAllValueMap() {
		return java.util.Collections.unmodifiableMap(cache);
	}
}
