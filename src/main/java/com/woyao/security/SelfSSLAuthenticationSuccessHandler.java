package com.woyao.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;

import com.snowm.security.profile.domain.Gender;
import com.snowm.security.web.authentication.SSLAuthenticationSuccessHandler;
import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.customer.chat.session.SessionContainer;
import com.woyao.customer.dto.ChatRoomDTO;
import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.service.IMobileService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;

public class SelfSSLAuthenticationSuccessHandler extends SSLAuthenticationSuccessHandler {

	private IShopAdminService shopAdminService;

	private CommonDao commonDao;

	private IMobileService mobileService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		super.onAuthenticationSuccess(request, response, authentication);
		HttpSession session = request.getSession();
		if (SelfSecurityUtils.hasAuthority(AuthorityConstants.SHOP_ADMIN)) {
			ShopDTO shop = this.getCurrentShop();
			long shopId = shop.getId();

			ChatRoomDTO room = this.mobileService.getChatRoom(shopId);
			long roomId = room.getId();
			session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);
			session.setAttribute(SessionContainer.SESSION_ATTR_SHOP_ID, shopId);

			ProfileDTO adminProfile = new ProfileDTO();
			adminProfile.setChatRoomId(roomId);
			adminProfile.setGender(Gender.OTHER);
			adminProfile.setHeadImg(shop.getPicUrl());
			adminProfile.setId(-10000L - shopId);
			adminProfile.setLatitude(shop.getLatitude());
			adminProfile.setLongitude(shop.getLongitude());
			adminProfile.setNickname(shop.getName());

			session.setAttribute(SessionContainer.SESSION_ATTR_CHATTER, adminProfile);
			session.setAttribute(SessionContainer.SESSION_ATTR_CHATROOM_ID, roomId);
			session.setAttribute(SessionContainer.SESSION_ATTR_ISDAPIN, true);
		}
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

	public void setShopAdminService(IShopAdminService shopAdminService) {
		this.shopAdminService = shopAdminService;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setMobileService(IMobileService mobileService) {
		this.mobileService = mobileService;
	}

}
