package com.snowm.cat.admin.service.impl;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.cat.admin.dto.BasePKDTO;
import com.snowm.cat.admin.service.IAdminService;
import com.snowm.cat.dao.CommonDao;
import com.snowm.hibernate.ext.domain.DefaultModelImpl;

public abstract class AbstractAdminService<M extends DefaultModelImpl, DTO extends BasePKDTO> implements IAdminService<M, DTO> {

	@Resource(name = "commonDao")
	protected CommonDao dao;

	protected Class<M> entityClazz;

	@SuppressWarnings("unchecked")
	public AbstractAdminService() {
		this.entityClazz = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public DTO get(long id) {
		M m = this.getById(id);
		if (m != null) {
			return this.transferToDTO(m, true);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public DTO get(long id, boolean isFull) {
		M m = this.getById(id);
		if (m != null) {
			return this.transferToDTO(m, isFull);
		}
		return null;
	}

	@Transactional
	@Override
	public DTO enable(long id) {
		M m = this.dao.get(this.entityClazz, id);
		if (m == null) {
			return null;
		}
		m.getLogicalDelete().setEnabled(true);
		return this.transferToDTO(m, false);
	}

	@Transactional
	@Override
	public DTO disable(long id) {
		M m = this.dao.get(this.entityClazz, id);
		if (m == null) {
			return null;
		}
		m.getLogicalDelete().setEnabled(false);
		return this.transferToDTO(m, false);
	}
	
	@Transactional
	@Override
	public DTO delete(long id) {
		M m = this.dao.get(this.entityClazz, id);
		if (m == null) {
			return null;
		}
		m.getLogicalDelete().setDeleted(true);
		return this.transferToDTO(m, false);
	}

	@Transactional
	@Override
	public DTO putback(long id) {
		M m = this.dao.get(this.entityClazz, id);
		if (m == null) {
			return null;
		}
		m.getLogicalDelete().setDeleted(false);
		return this.transferToDTO(m, false);
	}
	
	@Transactional
	@Override
	public DTO create(DTO dto) {
		M m = this.transferToDomain(dto);
		this.dao.save(m);
		DTO rs = this.get(m.getId());
		return rs;
	}

	protected M getById(long id) {
		M m = this.dao.get(this.entityClazz, id);
		return m;
	}

}
