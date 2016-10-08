package com.woyao.wx.third.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FunctionInfo {

	@JsonProperty("funcscope_category")
	private FunctionCategory funcscopeCategory;

	public FunctionCategory getFuncscopeCategory() {
		return funcscopeCategory;
	}

	public void setFuncscopeCategory(FunctionCategory funcscopeCategory) {
		this.funcscopeCategory = funcscopeCategory;
	}

}
