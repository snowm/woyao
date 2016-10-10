package com.woyao.customer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.GlobalConfig;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ShopDTO;
import com.woyao.customer.service.IMobileService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.domain.chat.ChatRoom;
import com.woyao.utils.DistanceUtils;

@Service("mobileService")
public class MobileServiceImpl implements IMobileService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "distanceUtils")
	private DistanceUtils distanceUtils;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
	public PaginationBean<ShopDTO> findShop(Double latitude, Double longitude, long pageNumber, int pageSize) {
		List<Criterion> criterions = new ArrayList<>();
		if (latitude != null && longitude != null) {
			DistanceUtils.SquareItudes squareItudes = this.distanceUtils.returnLLSquarePoint(latitude, longitude,
					globalConfig.getShopAvailableDistance());
			double topLat = squareItudes.getLeftTop()[0];
			double bottomLat = squareItudes.getLeftBottom()[0];
			double leftLng = squareItudes.getLeftTop()[1];
			double rightLng = squareItudes.getRightTop()[1];

			criterions.add(Restrictions.le("latitude", topLat));
			criterions.add(Restrictions.ge("latitude", bottomLat));
			criterions.add(Restrictions.le("longitude", rightLng));
			criterions.add(Restrictions.ge("longitude", leftLng));
		}
		criterions.add(Restrictions.eq("logicalDelete.deleted", false));

		PaginationBean<ShopDTO> pb = new PaginationBean<>();
		pb.setPageNumber(pageNumber);
		pb.setPageSize(pageSize);
		long count = this.dao.count(Shop.class, criterions);
		pb.setTotalCount(count);
		if (count > 0L) {
			List<Shop> result = this.dao.query(Shop.class, criterions, pageNumber, pageSize);
			List<ShopDTO> dtos = new ArrayList<>();
			for (Shop e : result) {
				ShopDTO dto = new ShopDTO();
				BeanUtils.copyProperties(e, dto);
				if (e.getPic() != null) {
					dto.setPicURL(e.getPic().getUrl());
				}
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

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
	@Override
	public Long calculateDistanceToShop(Double latitude, Double longitude, long shopId) {
		if (latitude == null || longitude == null) {
			return null;
		}
		Shop shop = this.dao.get(Shop.class, shopId);
		double distance = this.distanceUtils.distance(latitude, longitude, shop.getLatitude(), shop.getLongitude());
		Long x = Math.round(distance);
		return x;
	}

}
