package com.woyao.admin.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.security.SelfSecurityUtils;

@Component("shopRoot")
public class ShopRoot {
		
	@Resource(name = "commonDao")
	private CommonDao commonDao;
	
	@Resource(name = "shopAdminService")
	private IShopAdminService shopAdminService;

	@Transactional(readOnly = false, isolation = Isolation.READ_UNCOMMITTED)
	public ShopDTO getCurrentShop() {
		long profileId = SelfSecurityUtils.getCurrentProfile().getId();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("profileId", profileId);
		Shop currentShop = this.commonDao.queryUnique("from Shop where managerProfileId = :profileId", paramMap);
		if (currentShop == null) {
			throw new IllegalStateException();
		}
		ShopDTO dto = shopAdminService.transferToFullDTO(currentShop);
		dto.setManagerPwd(null);
		return dto;
	}
}
