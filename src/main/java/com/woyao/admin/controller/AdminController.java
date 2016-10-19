package com.woyao.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.security.AuthorityConstants;
import com.woyao.security.SelfSecurityUtils;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Resource(name = "shopAdminService")
	private IShopAdminService shopAdminService;
	
	@Resource(name = "commonDao")
	private CommonDao commonDao;
	
	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String rootHome(Model model) {
		boolean isShopAdmin = SelfSecurityUtils.hasAuthority(AuthorityConstants.SHOP_ADMIN);
		boolean isAdmin = SelfSecurityUtils.hasAuthority(AuthorityConstants.ADMIN)
				|| SelfSecurityUtils.hasAuthority(AuthorityConstants.SUPER);
		if (isShopAdmin && !isAdmin) {
			ShopDTO dto = getCurrentShop();
			model.addAttribute("shop", dto);
			return "shopAdminIndex";
		}
		return "adminIndex";
	}

	private ShopDTO getCurrentShop() {
		long profileId = SelfSecurityUtils.getCurrentProfile().getId();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("profileId", profileId);
		Shop currentShop = this.commonDao.queryUnique("from Shop where managerProfileId = :profileId", paramMap);
		if (currentShop == null) {
			throw new IllegalStateException();
		}
		ShopDTO dto = shopAdminService.get(currentShop.getId(), true);
		dto.setManagerPwd(null);
		return dto;
	}
}
