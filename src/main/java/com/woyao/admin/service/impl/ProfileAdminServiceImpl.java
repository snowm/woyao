package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.security.profile.domain.Profile;
import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryProfileRequestDTO;
import com.woyao.admin.dto.profile.ProfileDTO;
import com.woyao.admin.service.IProfileAdminService;
import com.woyao.admin.service.IUserAdminService;
import com.woyao.admin.shop.controller.ShopRoot;
import com.woyao.domain.Shop;
@Service("profileAdminService")
public class ProfileAdminServiceImpl extends AbstractAdminService<Profile, ProfileDTO> implements IProfileAdminService {

	
	@Resource(name = "userAdminService")
	private IUserAdminService userAdminService;
	
	@Resource(name="shopRoot")
	private ShopRoot shopRoot;
	
	@Resource(name = "woyaoPasswordEncoder")
	private PasswordEncoder passwordEncoder;
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ProfileDTO update(ProfileDTO dto) {
		Profile m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public PaginationBean<ProfileDTO> query(QueryProfileRequestDTO request) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (!StringUtils.isEmpty(request.getUsername())) {
			criterions.add(Restrictions.like("username", "%" + request.getUsername() + "%"));
		}
		if (!StringUtils.isEmpty(request.getNickname())) {
			criterions.add(Restrictions.like("nickname", "%" + request.getNickname() + "%"));
		}
		if (request.isEnabled()) {
			criterions.add(Restrictions.eq("enabled",  request.isEnabled()));
		}
		if (request.isExpired()) {
			criterions.add(Restrictions.eq("expired",  request.isExpired()));
		}
		if (request.getGenderId()!=null) {
			criterions.add(Restrictions.eq("genderId",  request.getGenderId()));
		}
		if (!StringUtils.isEmpty(request.getMobileNumber())) {
			criterions.add(Restrictions.like("mobileNumber", "%" + request.getMobileNumber() + "%"));
		}
		if (!StringUtils.isEmpty(request.getEmail())) {
			criterions.add(Restrictions.like("email", "%" + request.getEmail() + "%"));
		}	
		if (request.getDeleted() != null) {
			criterions.add(Restrictions.eq("logicalDelete.deleted", request.getDeleted()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<Profile> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}

		PaginationBean<ProfileDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<ProfileDTO> results = new ArrayList<>();
		for (Profile m : ms) {
			ProfileDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public Profile transferToDomain(ProfileDTO dto) {
		Profile m = new Profile();
		BeanUtils.copyProperties(dto, m);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());		
		return m;
	}

	@Override
	public ProfileDTO transferToSimpleDTO(Profile m) {
		ProfileDTO dto = new ProfileDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setGender(m.getGender());
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public ProfileDTO transferToFullDTO(Profile m) {
		
		return this.transferToSimpleDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public boolean resetProfilePwd(Long shopId) {
		Shop shop=this.dao.get(Shop.class, shopId);
		if(shop!=null){		
			Profile profile=this.dao.get(Profile.class, shop.getManagerProfileId());
			if(profile!=null){	
				ProfileDTO profileDTO=transferToSimpleDTO(profile);
				profileDTO.setPassword("888888");
				this.userAdminService.update(profileDTO);
				return true;		
			}
		}	
		return false;
	}

	/**
	 * retrun param:0：修改成功
	 * 				1：新旧密码不一致
	 * 				2：密码和确认密码不一致
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Integer updataProfilePwd(String oldPwd,String newPwd,String againPwd) {
		Integer num=null;
		Long shopId=shopRoot.getCurrentShop().getId();
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("shopId", shopId);
		String hql="from Profile as p where p.id in(select s.managerProfileId from Shop as s where s.id= :shopId)";
		Profile profile=this.dao.queryUnique(hql, paramMap);
		if(profile!=null){
			boolean flag=passwordEncoder.matches(oldPwd.trim(), profile.getPassword());	//旧密码和输入密码进行比较 验证是否相同
			if (flag) {
				if(newPwd.trim().isEmpty() && !againPwd.trim().isEmpty() && newPwd.equals(againPwd)){				
					ProfileDTO profileDTO=transferToSimpleDTO(profile);
					profileDTO.setPassword(newPwd);
					this.userAdminService.update(profileDTO);//重置密码
					num=0;
					return num;
				}else if(newPwd.trim().isEmpty() && !againPwd.trim().isEmpty()){//验证两次密码是否一致
					num=2;
					return num;
				}
			}else{//新旧密码不一致
				num=1;
				return num;
			}		 
		}
		/*Long shopId=shopRoot.getCurrentShop().getId();
		Shop shop=this.dao.get(Shop.class, shopId);
		if(newPwd.equals(againPwd)){
			Profile profile=this.dao.get(Profile.class, shop.getManagerProfileId());
			ProfileDTO profileDTO=transferToSimpleDTO(profile);
			profileDTO.setPassword(newPwd);
			this.userAdminService.update(profileDTO);
			return true;		
		}*/
		return null;
	}
}
