package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.profile.ProfileWXDTO;
import com.woyao.admin.dto.profile.QueryProfileWXRequestDTO;
import com.woyao.admin.service.IProfileWXAdminService;
import com.woyao.domain.profile.ProfileWX;
@Service("profileWXAdminService")
public class ProfileWXAdminServiceImpl extends AbstractAdminService<ProfileWX, ProfileWXDTO> implements IProfileWXAdminService {

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ProfileWXDTO update(ProfileWXDTO dto) {
		ProfileWX m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public PaginationBean<ProfileWXDTO> query(QueryProfileWXRequestDTO request) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!StringUtils.isEmpty(request.getNickname())) {
			criterions.add(Restrictions.like("nickname", "%" + request.getNickname() + "%"));
		}
		if (request.getGenderId()!=null) {
			criterions.add(Restrictions.eq("genderId",  request.getGenderId()));
		}
		if (!StringUtils.isEmpty(request.getCity())) {
			criterions.add(Restrictions.like("city", "%" + request.getCity() + "%"));
		}
		if (!StringUtils.isEmpty(request.getCountry())) {
			criterions.add(Restrictions.like("country", "%" + request.getCountry() + "%"));
		}
		if (request.getDeleted() != null) {
			criterions.add(Restrictions.eq("logicalDelete.deleted", request.getDeleted()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<ProfileWX> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}

		PaginationBean<ProfileWXDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<ProfileWXDTO> results = new ArrayList<>();
		for (ProfileWX m : ms) {
			ProfileWXDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public ProfileWX transferToDomain(ProfileWXDTO dto) {
		ProfileWX m = new ProfileWX();
		BeanUtils.copyProperties(dto, m);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());		
		return m;
	}

	@Override
	public ProfileWXDTO transferToSimpleDTO(ProfileWX m) {
		ProfileWXDTO dto = new ProfileWXDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setGenderId(m.getGender().getPersistedValue());
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public ProfileWXDTO transferToFullDTO(ProfileWX m) {
		
		return this.transferToSimpleDTO(m);
	}
}
