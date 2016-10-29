package com.woyao.customer.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.profile.ProfileWX;

@Component("profileWxService")
public class ProfileWxServiceImpl implements IProfileWxService {
	
	private static final String HQL_GET_BY_OPENID = "from ProfileWX where openId = :openId";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(readOnly = true)
	@Override
	public ProfileDTO getByOpenId(String openId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("openId", openId);
		ProfileWX m = this.dao.queryUnique(HQL_GET_BY_OPENID, paramMap);
		if (m == null) {
			return null;
		}
		ProfileDTO dto = new ProfileDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Transactional(readOnly = true)
	@Override
	public ProfileDTO getById(long id) {
		ProfileWX m = this.dao.get(ProfileWX.class, id);
		if (m == null) {
			return null;
		}
		ProfileDTO dto = new ProfileDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Transactional
	@Override
	public ProfileDTO saveProfileInfo(ProfileDTO dto) {
		Long id = dto.getId();
		if (id != null) {
			ProfileWX m = this.dao.get(ProfileWX.class, id);
			if (m != null) {
				BeanUtils.copyProperties(dto, m, "id");
				this.dao.update(m);
			}
		} else {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("openId", dto.getOpenId());
			ProfileWX existed = this.dao.queryUnique(HQL_GET_BY_OPENID, paramMap);
			if (existed == null) {
				existed = new ProfileWX();
			}
			BeanUtils.copyProperties(dto, existed, "id");
			this.dao.saveOrUpdate(existed);
			id = existed.getId();
		}

		return this.getById(id);
	}

}
