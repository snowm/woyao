package com.woyao.wx.validate;

public interface Validator<T> {

	ValidationResult validate(T content);
}
