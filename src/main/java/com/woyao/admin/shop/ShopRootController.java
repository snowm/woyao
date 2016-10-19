package com.woyao.admin.shop;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryProductsRequestDTO;
import com.woyao.admin.dto.product.ShopDTO;
import com.woyao.admin.service.IProductAdminService;
import com.woyao.admin.service.IShopAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.security.SelfSecurityUtils;

@Controller
@RequestMapping(value = "/shop/admin")
public class ShopRootController {

	@Resource(name = "shopAdminService")
	private IShopAdminService shopAdminService;

	@Resource(name = "productAdminService")
	private IProductAdminService productService;

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String rootHome(Model model) {
		ShopDTO dto = getCurrentShop();
		model.addAttribute("shop", dto);
		return "shopAdminIndex";
	}

	@RequestMapping(value = { "/dapin" }, method = RequestMethod.GET)
	public String dapin(Model model) {
		ShopDTO dto = getCurrentShop();
		model.addAttribute("shop", dto);
		return "shopIndex";
	}
	
	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProductDTO> query(QueryProductsRequestDTO request) {
		Long shopId = this.getCurrentShop().getId();
		request.setShopId(shopId);
		PaginationBean<ProductDTO> result = this.productService.query(request);
		return result;
	}

	private ShopDTO getCurrentShop() {
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
