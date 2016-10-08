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
import com.woyao.admin.dto.product.QueryShopsRequestDTO;
import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Pic;
import com.woyao.domain.Shop;
import com.woyao.domain.chat.ChatRoom;

@Service("shopAdminService")
public class ShopAdminServiceImpl extends AbstractAdminService<Shop, ShopDTO> implements IShopAdminService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "defaultProfileService")
	private ProfileService profileService;
	@Transactional
	@Override
	public ShopDTO create(ShopDTO dto) {
		Shop m = this.transferToDomain(dto);
		this.dao.save(m);
		ShopDTO rs = this.get(m.getId());
		return rs;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public ShopDTO update(ShopDTO dto) {
		Shop m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<ShopDTO> query(QueryShopsRequestDTO request) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!StringUtils.isEmpty(request.getName())) {
			criterions.add(Restrictions.like("name", "%" + request.getName() + "%"));
		}
		if (request.getDeleted() != null) {
			criterions.add(Restrictions.eq("logicalDelete.deleted", request.getDeleted()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<Shop> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}

		PaginationBean<ShopDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<ShopDTO> results = new ArrayList<>();
		for (Shop m : ms) {
			ShopDTO dto = this.transferToDTO(m, false);	
			String hql="from ChatRoom c where c.shop.id="+m.getId();	
			ChatRoom c=(ChatRoom)dao.queryUnique(hql);
			if(c!=null){				
				dto.setChatRoomId(c.getId());
				dto.setChatRoomName(c.getName());
			}
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public Shop transferToDomain(ShopDTO dto) {
		Shop m = new Shop();
		BeanUtils.copyProperties(dto, m);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		Pic pic = new Pic();
		pic.setId(dto.getId());
		m.setPic(pic);
		return m;
	}

	@Override
	public ShopDTO transferToSimpleDTO(Shop m) {
		ShopDTO dto = new ShopDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setPicId(m.getPic().getId());
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public ShopDTO transferToFullDTO(Shop m) {
		ShopDTO dto = this.transferToSimpleDTO(m);
		dto.setPicUrl(m.getPic().getUrl());
		String managerName = profileService.get(m.getManagerProfileId()).getUsername();
		dto.setManagerName(managerName);
		return dto;
	}
}
