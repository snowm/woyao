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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.snowm.security.profile.domain.Gender;
import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.QueryShopsRequestDTO;
import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.dto.profile.ProfileDTO;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.admin.service.IUserAdminService;
import com.woyao.cache.MsgProductCache;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Pic;
import com.woyao.domain.Shop;
import com.woyao.domain.chat.ChatRoom;

@Service("shopAdminService")
public class ShopAdminServiceImpl extends AbstractAdminService<Shop, ShopDTO> implements IShopAdminService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "userAdminService")
	private IUserAdminService userAdminService;

	@Resource(name = "msgProductCache")
	private MsgProductCache msgProductCache;
	
	@Transactional
	@Override
	public ShopDTO create(ShopDTO dto) {
		if (StringUtils.isEmpty(dto.getManagerName()) || StringUtils.isEmpty(dto.getManagerPwd())) {
			throw new RuntimeException("Name and password of manager should be not null!");
		}
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setUsername(dto.getManagerName());
		profileDTO.setPassword(dto.getManagerPwd());
		profileDTO.setGender(Gender.FEMALE);

		ProfileDTO savedProfileDTO = this.userAdminService.create(profileDTO);

		dto.setManagerProfileId(savedProfileDTO.getId());
		Shop m = this.transferToDomain(dto);
		this.dao.save(m);
		
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setShop(m);
		chatRoom.setName(m.getName());
		this.dao.save(chatRoom);

		this.msgProductCache.addShop(m.getId());
		ShopDTO rs = this.get(m.getId());
		return rs;
	}
	
	@Transactional
	@Override
	public ShopDTO delete(long id) {
		Shop m = this.dao.get(Shop.class, id);
		if (m == null) {
			return null;
		}
		m.getLogicalDelete().setDeleted(true);
		this.msgProductCache.deleteShop(id);
		return this.transferToDTO(m, false);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public ShopDTO update(ShopDTO dto) {
		Shop existed = this.dao.get(Shop.class, dto.getId());
		Assert.notNull(existed, "shot not exist!");
		long managerProfileId = existed.getManagerProfileId();
		Shop m = this.transferToDomain(dto);
		BeanUtils.copyProperties(m, existed, "managerProfileId", "id");

		Map<String, Object> params = new HashMap<>();
		params.put("shopId", dto.getId());
		ChatRoom room = this.dao.queryUnique("from ChatRoom where shop.id = :shopId", params);
		if (room == null) {
			room = new ChatRoom();
			room.setShop(m);
			room.setName(m.getName());
			this.dao.save(room);
		}		
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setId(managerProfileId);
		profileDTO.setUsername(dto.getManagerName());
		profileDTO.setPassword(dto.getManagerPwd());
		profileDTO.setGender(Gender.FEMALE);
		this.userAdminService.update(profileDTO);
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
			ShopDTO dto = transferToFullDTO(m);
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
		pic.setId(dto.getPicId());
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
		ProfileDTO p = userAdminService.get(m.getManagerProfileId());
		dto.setManagerName(p.getUsername());
		dto.setManagerType(p.getType().getTypeValue());
		dto.setManagerPwd(p.getPassword());
		return dto;
	}
}
