package com.woyao.wx.service;

import com.woyao.wx.dto.GetUserInfoResponse;

public interface IWxService {

	GetUserInfoResponse getUserInfo(Long profileWXId, String appId, String appSecret, String code);
	
}
