package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.security.profile.service.ProfileService;
import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.PicDTO;
import com.woyao.admin.dto.product.QueryPicRequestDTO;
import com.woyao.admin.service.IPicAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Pic;

@Service("picAdminService")
public class PicAdminServiceImpl extends AbstractAdminService<Pic, PicDTO> implements IPicAdminService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "defaultProfileService")
	private ProfileService profileService;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public PicDTO update(PicDTO dto) {
		Pic m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<PicDTO> query(QueryPicRequestDTO request) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!StringUtils.isEmpty(request.getUrl())) {
			criterions.add(Restrictions.like("url", "%" + request.getUrl() + "%"));
		}
		if (request.getDeleted() != null) {
			criterions.add(Restrictions.eq("logicalDelete.deleted", request.getDeleted()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<Pic> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}

		PaginationBean<PicDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<PicDTO> results = new ArrayList<>();
		for (Pic m : ms) {
			PicDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public Pic transferToDomain(PicDTO dto) {
		Pic m=new Pic();
		BeanUtils.copyProperties(dto, m);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		return m;
	}

	@Override
	public PicDTO transferToSimpleDTO(Pic m) {
		PicDTO dto=new PicDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public PicDTO transferToFullDTO(Pic m) {
		
		return transferToSimpleDTO(m);
	}
}
