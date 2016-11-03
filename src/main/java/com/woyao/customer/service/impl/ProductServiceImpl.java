package com.woyao.customer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.cache.MsgProductCache;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;
import com.woyao.customer.service.IProductService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;
import com.woyao.domain.product.MsgProduct;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductType;
import com.woyao.domain.product.ShopMsgProductConfig;

@Component("productService")
public class ProductServiceImpl implements IProductService, InitializingBean {

	private static final String HQL_LIST_PROD_SHOP = "from Product where shop.id = :shopId "
			+ "and type = :type and logicalDelete.deleted = false";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "msgProductCache")
	private MsgProductCache msgProductCache;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.warn("Start to load msg product cache!");
		long start = System.currentTimeMillis();
		List<MsgProductDTO> products = this.listAllGlobalMsgProduct();
		products.forEach(p -> this.msgProductCache.updateGlobalMsgProduct(p));

		List<Shop> shops = this.dao.query("from Shop where logicalDelete.deleted = false");
		shops.forEach(s -> this.msgProductCache.addShop(s.getId()));

		for (Shop s : shops) {
			Map<String, Object> paramMap = new HashMap<>();
			long shopId = s.getId();
			paramMap.put("shopId", shopId);
			List<ShopMsgProductConfig> mps = this.dao.query("from ShopMsgProductConfig where shopId = :shopId", paramMap);
			if (mps == null || mps.isEmpty()) {
				continue;
			}
			for (ShopMsgProductConfig mp : mps) {
				MsgProductDTO tmpP = this.msgProductCache.getShopMsgProduct(shopId, mp.getReferencedProductId());
				tmpP.setUnitPrice((float) mp.getUnitPrice() / 100);
				tmpP.setUnitPriceCent(mp.getUnitPrice());
			}
		}
		logger.warn("Msg product cache loaded, spent:{} ms!", System.currentTimeMillis() - start);
	}

	@Transactional(readOnly = true)
	@Override
	public MsgProductDTO getMsgProduct(long shopId, long msgProductId) {
		return this.msgProductCache.getShopMsgProduct(shopId, msgProductId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MsgProductDTO> listAllMsgProductFromCache(long shopId) {
		Map<Long, MsgProductDTO> mps = this.msgProductCache.getShopMsgProducts(shopId);

		List<MsgProductDTO> rs = new ArrayList<>();
		if (mps == null || mps.isEmpty()) {
			return rs;
		}
		mps.forEach((k, v) -> {
			rs.add(v);
		});
		return rs;
	}

	@Override
	public List<MsgProductDTO> listAllGlobalMsgProduct() {
		List<MsgProduct> mps = this.dao.query("from MsgProduct where shop.id is null and logicalDelete.deleted = false");
		List<MsgProductDTO> rs = new ArrayList<>();
		if (mps == null || mps.isEmpty()) {
			return rs;
		}
		for (MsgProduct mp : mps) {
			rs.add(transferToDTO(mp));
		}
		return rs;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProductDTO> listProducts(long shopId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("shopId", shopId);
		paramMap.put("type", ProductType.MATERIA);
		List<Product> products = this.dao.query(HQL_LIST_PROD_SHOP, paramMap);
		List<ProductDTO> rs = new ArrayList<>();
		if (products == null || products.isEmpty()) {
			return rs;
		}
		for (Product prod : products) {
			rs.add(transferToDTO(prod));
		}
		return rs;
	}

	private MsgProductDTO transferToDTO(MsgProduct msgProduct) {
		MsgProductDTO dto = new MsgProductDTO();
		BeanUtils.copyProperties(msgProduct, dto);
		if (msgProduct.getPic() != null) {
			dto.setPicURL(msgProduct.getPic().getUrl());
		}
		dto.setUnitPrice((float) msgProduct.getUnitPrice() / 100);
		dto.setUnitPriceCent(msgProduct.getUnitPrice());
		return dto;
	}

	private ProductDTO transferToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(product, dto);
		if (product.getPic() != null) {
			dto.setPicURL(product.getPic().getUrl());
		}
		dto.setUnitPrice((float) product.getUnitPrice() / 100);
		dto.setUnitPriceCent(product.getUnitPrice());
		return dto;
	}

}
