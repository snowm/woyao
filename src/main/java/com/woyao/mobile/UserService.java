package com.woyao.mobile;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.dao.CommonDao;
import com.woyao.domain.User;

@Service("userService")

public class UserService {
	@Resource(name = "commonDao")
	private CommonDao dao;
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
	public User getUser(String userId){
		
		return dao.get(User.class, userId);
	}
}
