package com.woyao.customer.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.service.IProfileWxService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.profile.ProfileWX;

@Component("profileWxService")
public class ProfileWxServiceImpl implements IProfileWxService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(readOnly = true)
	@Override
	public ChatterDTO getByOpenId(String openId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("openId", openId);
		ProfileWX profile = this.dao.queryUnique("from ProfileWX where openId = :openId", paramMap);
		if (profile == null) {
			return null;
		}
		ChatterDTO dto = new ChatterDTO();
		BeanUtils.copyProperties(profile, dto);
		return dto;
	}

	@Transactional(readOnly = true)
	@Override
	public ChatterDTO getById(long id) {
		ProfileWX m = this.dao.get(ProfileWX.class, id);
		if (m == null) {
			return null;
		}
		ChatterDTO dto = new ChatterDTO();
		BeanUtils.copyProperties(m, dto);
		return dto;
	}

	@Transactional
	@Override
	public ChatterDTO saveChatterInfo(ChatterDTO dto) {
		Long id = dto.getId();
		if (id != null) {
			ProfileWX m = this.dao.get(ProfileWX.class, id);
			if (m != null) {
				BeanUtils.copyProperties(dto, m, "id");
				this.dao.update(m);
			}
		} else {
			ProfileWX m = new ProfileWX();
			BeanUtils.copyProperties(dto, m);
			id = this.dao.save(m);
		}
		
		return this.getById(id);
	}

}
