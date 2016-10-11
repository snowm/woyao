package com.woyao.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.dao.CommonDao;
import com.woyao.domain.wx.UserAccessToken;

@Service("userAccessTokenService")
public class UserAccessTokenService {

	private static final String HQL_GET_BY_PROFILE_ID = "from UserAccessToken where profileWXId = :profileWXId order by id desc";
	
	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional
	public UserAccessToken getTokenByProfileId(Long profileWXId) {
		Map<String, Object> paramMap = new HashMap<>();
		UserAccessToken token = this.dao.queryUnique(HQL_GET_BY_PROFILE_ID, paramMap);
		return token;
	}

	@Transactional
	public void saveOrUpdate(UserAccessToken token) {
		token.getProfileWXId();
		this.dao.saveOrUpdate(token);
	}

}
