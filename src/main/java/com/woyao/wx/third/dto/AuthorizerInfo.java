package com.woyao.wx.third.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizerInfo {

	@JsonProperty("nick_name")
	private String nick_name;

	@JsonProperty("head_img")
	private String head_img;

	@JsonProperty("service_type_info")
	private TypeInfo serviceTypeInfo;

	@JsonProperty("verify_type_info")
	private TypeInfo verifyTypeInfo;

	@JsonProperty("business_info")
	private Map<String, Integer> businessInfo;

	@JsonProperty("alias")
	private String alias;

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public TypeInfo getServiceTypeInfo() {
		return serviceTypeInfo;
	}

	public void setServiceTypeInfo(TypeInfo serviceTypeInfo) {
		this.serviceTypeInfo = serviceTypeInfo;
	}

	public TypeInfo getVerifyTypeInfo() {
		return verifyTypeInfo;
	}

	public void setVerifyTypeInfo(TypeInfo verifyTypeInfo) {
		this.verifyTypeInfo = verifyTypeInfo;
	}

	public Map<String, Integer> getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(Map<String, Integer> businessInfo) {
		this.businessInfo = businessInfo;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
