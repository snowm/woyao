package com.snowm.cat.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.cat.admin.dto.product.CategoryDTO;
import com.snowm.cat.admin.dto.product.QueryCategoriesRequestDTO;
import com.snowm.cat.admin.service.ICategoryAdminService;
import com.snowm.cat.domain.product.Category;
import com.snowm.utils.query.PaginationBean;

@Service("categoryAdminService")
public class CategoryAdminServiceImpl extends AbstractAdminService<Category, CategoryDTO> implements ICategoryAdminService {

	@Transactional
	@Override
	public PaginationBean<CategoryDTO> query(QueryCategoriesRequestDTO queryRequest) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		String name = queryRequest.getName();
		if (!StringUtils.isEmpty(name)) {
			criterions.add(Restrictions.like("name", "%" + name + "%"));
		}

		String code = queryRequest.getCode();
		if (!StringUtils.isEmpty(code)) {
			criterions.add(Restrictions.eq("code", code));
		}

		Boolean enabled = queryRequest.getEnabled();
		if (enabled != null) {
			criterions.add(Restrictions.eq("logicalDelete.enabled", enabled));
		}
		long count = this.dao.count(this.entityClazz, criterions);
		List<Category> ms = new ArrayList<>();
		long pageNumber = queryRequest.getPageNumber();
		int pageSize = queryRequest.getPageSize();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, pageNumber, pageSize);
		}

		PaginationBean<CategoryDTO> rs = new PaginationBean<>(pageNumber, pageSize);
		rs.setTotalCount(count);
		List<CategoryDTO> results = new ArrayList<>();
		for (Category m : ms) {
			CategoryDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Transactional
	@Override
	public CategoryDTO update(CategoryDTO dto) {
		Category m = this.dao.get(Category.class, dto.getId());
		if (m == null) {
			return null;
		}
		BeanUtils.copyProperties(dto, m);
		this.dao.update(m);
		CategoryDTO rs = this.transferToDTO(m, true);
		return rs;
	}

	@Override
	public Category transferToDomain(CategoryDTO dto) {
		Category m = new Category();
		BeanUtils.copyProperties(dto, m, "parent");
		CategoryDTO parentDTO = dto.getParent();
		if (parentDTO != null) {
			Category parent = new Category();
			parent.setId(parentDTO.getId());
			m.setParent(parent);
		}
		return m;
	}

	@Override
	public CategoryDTO transferToSimpleDTO(Category m) {
		CategoryDTO dto = new CategoryDTO();
		BeanUtils.copyProperties(m, dto, "parent");
		Category parent = m.getParent();
		if (parent != null) {
			CategoryDTO parentDTO = new CategoryDTO();
			parentDTO.setId(parent.getId());
			parentDTO.setName(parent.getName());
			parentDTO.setCode(parent.getCode());
		}
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		return dto;
	}

	@Override
	public CategoryDTO transferToFullDTO(Category m) {
		return this.transferToSimpleDTO(m);
	}

}
