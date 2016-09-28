package com.woyao.domain.product;

import java.util.Map;
import java.util.TreeMap;

import com.snowm.hibernate.ext.EnumPersistedValueNotPresentException;
import com.snowm.hibernate.ext.usertype.PersistEnum;

public enum ProductType implements PersistEnum<ProductType> {

	MATERIA(1, "MATERIA"), MSG(2, "MSG");

	public final static String CLASS_NAME = "com.woyao.domain.product.ProductType";

	private int typeValue;

	private String desc;

	private final static Map<Integer, ProductType> cache = new TreeMap<>();

	static {
		for (ProductType e : ProductType.values()) {
			cache.put(e.getPersistedValue(), e);
		}
	}

	private ProductType(int typeValue, String desc) {
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

	public static ProductType getEnum(Integer persistedValue) {
		if (persistedValue == null) {
			return null;
		}
		ProductType e = cache.get(persistedValue);
		if (e == null) {
			throw new EnumPersistedValueNotPresentException(persistedValue, ProductType.class);
		}
		return e;
	}

	public static Map<Integer, ProductType> getAllValueMap() {
		return java.util.Collections.unmodifiableMap(cache);
	}
}
