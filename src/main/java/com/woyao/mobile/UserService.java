package com.woyao.mobile;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.dao.CommonDao;
import com.woyao.domain.User;

@Service("userService")
@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
public class UserService {
	@Resource(name = "commonDao")
	private CommonDao dao;
	
	public User getUser(User user){
		
		return dao.get(User.class, user.getUserId());
	}
}
