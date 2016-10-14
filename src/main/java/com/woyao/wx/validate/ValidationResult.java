package com.woyao.wx.validate;

import java.io.Serializable;

public class ValidationResult implements Serializable {

	private static final long serialVersionUID = -453232701228412740L;
	private String errorCode;
	private String errorDesc;

	public ValidationResult(String errorCode, String errorDesc) {
		super();
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}


}
