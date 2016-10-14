package com.woyao.wx.service;

import com.woyao.wx.dto.GetUserInfoResponse;

public interface IWxService {

	GetUserInfoResponse getUserInfoViaExistedOpenId(String openId, String appId, String appSecret);

	GetUserInfoResponse getUserInfo(String appId, String appSecret, String code);

}
