package com.woyao.customer.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ShopDTO;

public interface IMobileService {
	
	PaginationBean<ShopDTO> findShop(Double latitude, Double longitude, long pageNumber, int pageSize);

	ChatRoomDTO getChatRoom(long shopId);
}
