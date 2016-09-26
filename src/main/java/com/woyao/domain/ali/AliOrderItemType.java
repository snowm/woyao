package com.woyao.domain.ali;

import java.util.Map;
import java.util.TreeMap;

import com.snowm.hibernate.ext.EnumPersistedValueNotPresentException;
import com.snowm.hibernate.ext.usertype.PersistEnum;

public enum AliOrderItemType implements PersistEnum<AliOrderItemType> {

	IN_STOCK(1, "IN_STOCK"), IN_TRANSIT(2, "IN_TRANSIT"), AGENT(3, "AGENT");

	public final static String CLASS_NAME = "com.woyao.domain.ali.AliOrderItemType";

	private int typeValue;

	private String desc;

	private final static Map<Integer, AliOrderItemType> cache = new TreeMap<>();

	static {
		for (AliOrderItemType e : AliOrderItemType.values()) {
			cache.put(e.getPersistedValue(), e);
		}
	}

	private AliOrderItemType(int typeValue, String desc) {
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

	public static AliOrderItemType getEnum(Integer persistedValue) {
		if (persistedValue == null) {
			return null;
		}
		AliOrderItemType e = cache.get(persistedValue);
		if (e == null) {
			throw new EnumPersistedValueNotPresentException(persistedValue, AliOrderItemType.class);
		}
		return e;
	}

	public static Map<Integer, AliOrderItemType> getAllValueMap() {
		return java.util.Collections.unmodifiableMap(cache);
	}
}
