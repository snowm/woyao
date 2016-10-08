package com.woyao.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.dao.CommonDao;
import com.woyao.domain.wx.GlobalAccessToken;

@Service("globalAccessTokenService")
public class GlobalAccessTokenService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional
	public GlobalAccessToken getToken() {
		GlobalAccessToken token = this.dao.queryUnique("from GlobalAccessToken order by id desc");
		return token;
	}

	@Transactional
	public void saveOrUpdate(GlobalAccessToken token) {
		this.dao.saveOrUpdate(token);
	}

}
