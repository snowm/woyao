package com.snowm.cat.admin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.security.profile.dao.impl.PermissionDaoImpl;
import com.snowm.security.profile.domain.Permission;

@Service("mockService2")
public class MockService2 {

	@Resource(name = "defaultPermissionDao")
	private PermissionDaoImpl dao;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void alwException2() {
		Permission permission = new Permission();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			sb.append("code");
		}
		permission.setCode(sb.toString());
		permission.setDescription("description");
		permission.setName("name");
		dao.saveOrUpdate(permission);
	}
}
