package com.woyao.wx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.wx.dto.GetUserInfoResponse;
import com.woyao.wx.service.IWxService;

@Service("wxService")
public class WxServiceImpl implements IWxService {

	@Resource(name = "profileWxService")
	private IProfileWxService profileWxService;

	@Override
	public GetUserInfoResponse getUserInfo(Long profileWXId, String appId, String appSecret, String code) {
		if (profileWXId != null) {
			ChatterDTO chatter = this.profileWxService.getById(profileWXId);
		}
		return null;
	}

}
