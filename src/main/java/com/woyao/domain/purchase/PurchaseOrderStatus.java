package com.woyao.domain.purchase;

import java.util.Map;
import java.util.TreeMap;

import com.snowm.hibernate.ext.EnumPersistedValueNotPresentException;
import com.snowm.hibernate.ext.usertype.PersistEnum;

public enum PurchaseOrderStatus implements PersistEnum<PurchaseOrderStatus> {

	CREATED(1, "CREATED"), DELIVERED(2, "DELIVERED"), ARRIVED(3, "ARRIVED");

	public final static String CLASS_NAME = "com.woyao.domain.purchase.PurchaseOrderStatus";

	private int typeValue;

	private String desc;

	private final static Map<Integer, PurchaseOrderStatus> cache = new TreeMap<>();

	static {
		for (PurchaseOrderStatus e : PurchaseOrderStatus.values()) {
			cache.put(e.getPersistedValue(), e);
		}
	}

	private PurchaseOrderStatus(int typeValue, String desc) {
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

	public static PurchaseOrderStatus getEnum(Integer persistedValue) {
		if (persistedValue == null) {
			return null;
		}
		PurchaseOrderStatus e = cache.get(persistedValue);
		if (e == null) {
			throw new EnumPersistedValueNotPresentException(persistedValue, PurchaseOrderStatus.class);
		}
		return e;
	}

	public static Map<Integer, PurchaseOrderStatus> getAllValueMap() {
		return java.util.Collections.unmodifiableMap(cache);
	}
}
