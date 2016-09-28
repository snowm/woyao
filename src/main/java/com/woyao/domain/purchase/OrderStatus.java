package com.woyao.domain.purchase;

import java.util.Map;
import java.util.TreeMap;

import com.snowm.hibernate.ext.EnumPersistedValueNotPresentException;
import com.snowm.hibernate.ext.usertype.PersistEnum;

public enum OrderStatus implements PersistEnum<OrderStatus> {

	DELIVERED(1, "DELIVERED"), SUCCESS(2, "SUCCESS"), FAIL(3, "FAIL");

	public final static String CLASS_NAME = "com.woyao.domain.purchase.OrderStatus";

	private int typeValue;

	private String desc;

	private final static Map<Integer, OrderStatus> cache = new TreeMap<>();

	static {
		for (OrderStatus e : OrderStatus.values()) {
			cache.put(e.getPersistedValue(), e);
		}
	}

	private OrderStatus(int typeValue, String desc) {
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

	public static OrderStatus getEnum(Integer persistedValue) {
		if (persistedValue == null) {
			return null;
		}
		OrderStatus e = cache.get(persistedValue);
		if (e == null) {
			throw new EnumPersistedValueNotPresentException(persistedValue, OrderStatus.class);
		}
		return e;
	}

	public static Map<Integer, OrderStatus> getAllValueMap() {
		return java.util.Collections.unmodifiableMap(cache);
	}
}
