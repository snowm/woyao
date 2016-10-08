package com.woyao.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ShopDTO;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.domain.chat.ChatRoom;

@Service("mobileService")
public class MobileService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
	public PaginationBean<ShopDTO> findShop(long pageNumber, int pageSize) {
		List<Criterion> criterions = new ArrayList<>();

		PaginationBean<ShopDTO> pb = new PaginationBean<>();
		pb.setPageNumber(pageNumber);
		pb.setPageSize(pageSize);
		long count = this.dao.count(Shop.class, criterions);
		pb.setTotalCount(count);
		if (count > 0) {
			List<Shop> result = this.dao.query(Shop.class, criterions, pageNumber, pageSize);
			List<ShopDTO> dtos = new ArrayList<>();
			for (Shop e : result) {
				ShopDTO dto = new ShopDTO();
				BeanUtils.copyProperties(e, dto);
				dtos.add(dto);
			}
			pb.setResults(dtos);
		}
		return pb;
	}

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
	public ChatRoomDTO getChatRoom(long shopId) {
		Map<String, Object> params = new HashMap<>();
		params.put("shopId", shopId);
		ChatRoom room = this.dao.queryUnique("from ChatRoom where shop.id = :shopId", params);
		if (room == null) {
			return null;
		}
		ChatRoomDTO rs = new ChatRoomDTO();
		BeanUtils.copyProperties(room, rs);
		return rs;
	}

}
