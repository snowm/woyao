package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ChatMsgDTO;
import com.woyao.admin.dto.product.QueryChatMsgRequestDTO;
import com.woyao.admin.service.IChatMsgAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.chat.ChatMsg;
import com.woyao.domain.chat.ChatRoom;
import com.woyao.domain.product.Product;
@Service("chatMsgAdminService")
public class ChatAMsgdminServiceImpl extends AbstractAdminService<ChatMsg, ChatMsgDTO> implements IChatMsgAdminService{

	@Resource(name = "commonDao")
	private CommonDao dao;

	public ChatMsgDTO update(ChatMsgDTO dto) {
		ChatMsg m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public PaginationBean<ChatMsgDTO> query(QueryChatMsgRequestDTO request) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		/*if (!StringUtils.isEmpty(request.getName())) {
			criterions.add(Restrictions.like("name", "%" + request.getName() + "%"));
		}*/
		if (request.isFree()) {
			criterions.add(Restrictions.eq("free",request.getFrom()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<ChatMsg> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}
		PaginationBean<ChatMsgDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<ChatMsgDTO> results = new ArrayList<>();
		for (ChatMsg m : ms) {
			ChatMsgDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return null;
	}
	@Override
	public ChatMsg transferToDomain(ChatMsgDTO dto) {
		ChatMsg m=new ChatMsg();
		BeanUtils.copyProperties(dto, m);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		return m;
	}
	@Override
	public ChatMsgDTO transferToSimpleDTO(ChatMsg m) {
		ChatMsgDTO dto=new ChatMsgDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setChatRoomName(dao.get(ChatRoom.class, m.getChatRoomId()).getName());
		dto.setProductName(dao.get(Product.class, m.getProductId()).getName());
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}
	@Override
	public ChatMsgDTO transferToFullDTO(ChatMsg m) {
		// TODO Auto-generated method stub
		return transferToSimpleDTO(m);
	}	
}
