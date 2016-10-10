package com.woyao.customer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;
import com.woyao.customer.service.IProductService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.product.PaidMsgProductConfig;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductType;

@Component("productService")
public class ProductServiceImpl implements IProductService {

	private static final String HQL_GET_MSG_PROD_VIA_MSG_CONFIG_ID = "from PaidMsgProductConfig where product.id = :productId and logicalDelete.deleted = false";

	private static final String HQL_LIST_PROD_SHOP = "from Product where shop.id = :shopId "
			+ "and type = :type and logicalDelete.deleted = false";

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(readOnly = true)
	@Override
	public MsgProductDTO getMsgProduct(long msgProductId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", msgProductId);
		PaidMsgProductConfig msgProductConfig = this.dao.queryUnique(HQL_GET_MSG_PROD_VIA_MSG_CONFIG_ID, paramMap);
		if (msgProductConfig == null) {
			return null;
		}
		return transferToDTO(msgProductConfig);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MsgProductDTO> listAllMsgProduct() {
		List<PaidMsgProductConfig> ms = this.dao.query("from PaidMsgProductConfig where logicalDelete.deleted = false");
		List<MsgProductDTO> rs = new ArrayList<>();
		if (ms == null || ms.isEmpty()) {
			return rs;
		}
		for (PaidMsgProductConfig mp : ms) {
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

	private MsgProductDTO transferToDTO(PaidMsgProductConfig msgProductConfig) {
		MsgProductDTO dto = new MsgProductDTO();
		BeanUtils.copyProperties(msgProductConfig.getProduct(), dto);
		dto.setHoldTime(msgProductConfig.getHoldTime());
		return dto;
	}

	private ProductDTO transferToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(product, dto);
		dto.setPicURL(product.getPic().getUrl());
		return dto;
	}
}
