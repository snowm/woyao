package com.woyao.wx.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.util.ReflectionUtils.FieldFilter;

public class SignValidateFieldFilter implements FieldFilter {

	private String[] excludingFields = {};

	public SignValidateFieldFilter(String[] excludingFields) {
		super();
		if (excludingFields != null) {
			this.excludingFields = excludingFields;
		}
	}

	@Override
	public boolean matches(Field field) {
		for (String ex : this.excludingFields) {
			if (field.getName().equals(ex)) {
				return false;
			}
		}
		int mod = field.getModifiers();
		return !Modifier.isStatic(mod) && !Modifier.isFinal(mod);
	}

}
