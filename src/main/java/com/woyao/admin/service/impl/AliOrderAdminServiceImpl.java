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
import org.springframework.transaction.annotation.Transactional;

import com.woyao.admin.dto.ali.AliOrderDTO;
import com.woyao.admin.dto.ali.QueryAliOrdersRequestDTO;
import com.woyao.admin.service.IAliOrderAdminService;
import com.woyao.domain.ali.AliOrder;
import com.snowm.utils.query.PaginationBean;

@Service("aliOrderAdminService")
public class AliOrderAdminServiceImpl extends AbstractAdminService<AliOrder, AliOrderDTO> implements IAliOrderAdminService {
	
	@Transactional(readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<AliOrderDTO> query(QueryAliOrdersRequestDTO queryRequest) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!StringUtils.isEmpty(queryRequest.getTbOrderNo())) {
			criterions.add(Restrictions.eq("tbOrderNo", queryRequest.getTbOrderNo()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("logicalDelete.enabled"));
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<AliOrder> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, queryRequest.getPageNumber(), queryRequest.getPageSize());
		}

		PaginationBean<AliOrderDTO> rs = new PaginationBean<>(queryRequest.getPageNumber(), queryRequest.getPageSize());
		rs.setTotalCount(count);
		List<AliOrderDTO> results = new ArrayList<>();
		for (AliOrder m : ms) {
			AliOrderDTO dto = this.transferToDTO(m, true);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public AliOrderDTO update(AliOrderDTO dto) {
		return dto;
	}

	@Override
	public AliOrderDTO transferToSimpleDTO(AliOrder m) {
		AliOrderDTO dto = new AliOrderDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public AliOrder transferToDomain(AliOrderDTO dto) {
		return null;
	}

	@Override
	public AliOrderDTO transferToFullDTO(AliOrder m) {
		return null;
	}

}
