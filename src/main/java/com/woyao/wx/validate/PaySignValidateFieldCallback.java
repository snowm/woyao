package com.woyao.wx.validate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.http.NameValuePair;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.woyao.wx.WxUtils;

public class PaySignValidateFieldCallback implements FieldCallback {

	private List<NameValuePair> nvs = new ArrayList<>();

	private Object obj;

	public PaySignValidateFieldCallback(Object obj) {
		super();
		this.obj = obj;
	}

	@Override
	public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.makeAccessible(field);
		Object value = ReflectionUtils.getField(field, obj);
		if (value == null) {
			return;
		}
		String valueStr = value.toString();
		XmlElement xmlEleAnnotation = field.getAnnotation(XmlElement.class);
		String name = xmlEleAnnotation.name();
		this.nvs.add(WxUtils.generateNVPair(name, valueStr));
	}

	public List<NameValuePair> getNvs() {
		return this.nvs;
	}
}
