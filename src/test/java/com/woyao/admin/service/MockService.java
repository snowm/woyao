package com.woyao.admin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.security.profile.dao.impl.PermissionDaoImpl;
import com.snowm.security.profile.domain.Permission;

@Service("mockService")
public class MockService {

	@Resource(name = "defaultPermissionDao")
	private PermissionDaoImpl dao;
	@Resource(name = "mockService2")
	private MockService2 mockService2;

	@Transactional
	public void delete(long id) {
		dao.delete(id);
	}

	@Transactional
	public Permission get(long id) {
		return dao.get(id);
	}

	@Transactional
	public void insert4(Permission permission) {
		this.insert4_internal(permission);
	}
	
	public void insert4_internal(Permission permission) {
		dao.saveOrUpdate(permission);
		this.alwException1();
	}
	
	public void insert4_1(Permission permission) {
		this.insert4_1_internal(permission);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insert4_1_internal(Permission permission) {
		dao.saveOrUpdate(permission);
		this.alwException1();
	}

	public void insert5(Permission permission) {
		this.insert5_internal(permission);
	}
	
	@Transactional
	private void insert5_internal(Permission permission) {
		dao.saveOrUpdate(permission);
		this.alwException1();
	}
	
	
	@Transactional
	public void insert1(Permission permission) {
		dao.saveOrUpdate(permission);
		try {
			this.alwException1();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Transactional
	public void insert2(Permission permission) {
		dao.saveOrUpdate(permission);
		try {
			this.alwException2();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Transactional(noRollbackFor = org.springframework.dao.DataIntegrityViolationException.class)
	public void insert3(Permission permission) {
		dao.saveOrUpdate(permission);
		this.mockService2.alwException2();
	}

	public void alwException1() {
		throw new RuntimeException("test");
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void alwException2() {
		Permission permission = new Permission();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			sb.append("code1");
		}
		permission.setCode(sb.toString());
		permission.setDescription("description");
		permission.setName("name");
		dao.saveOrUpdate(permission);
	}
}
