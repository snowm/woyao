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

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ChatRoomDTO;
import com.woyao.admin.dto.product.QueryChatRequestDTO;
import com.woyao.admin.service.IChatAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.domain.chat.ChatRoom;
@Service("chatAdminService")
public class ChatAdminServiceImpl extends AbstractAdminService<ChatRoom, ChatRoomDTO> implements IChatAdminService{

	@Resource(name = "commonDao")
	private CommonDao dao;

	public ChatRoomDTO update(ChatRoomDTO dto) {
		ChatRoom m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public PaginationBean<ChatRoomDTO> query(QueryChatRequestDTO request) {
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
		List<ChatRoom> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}
		PaginationBean<ChatRoomDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<ChatRoomDTO> results = new ArrayList<>();
		for (ChatRoom m : ms) {
			ChatRoomDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}
	@Override
	public ChatRoom transferToDomain(ChatRoomDTO dto) {
		ChatRoom m=new ChatRoom();
		BeanUtils.copyProperties(dto, m);
		Shop shop=new Shop();
		shop.setId(dto.getId());
		shop.setName(dto.getName());
		m.setShop(shop);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		return m;
	}
	@Override
	public ChatRoomDTO transferToSimpleDTO(ChatRoom m) {
		ChatRoomDTO dto=new ChatRoomDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setShopId(m.getShop().getId());
		dto.setShopName(m.getShop().getName());
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}
	@Override
	public ChatRoomDTO transferToFullDTO(ChatRoom m) {
		// TODO Auto-generated method stub
		return transferToSimpleDTO(m);
	}	
}
