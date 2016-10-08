package com.woyao.wx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

	@JsonProperty("errcode")
	private String errCode;

	@JsonProperty("errmsg")
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
