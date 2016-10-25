package com.woyao.customer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;
import com.woyao.customer.service.IProductService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.product.MsgProduct;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductType;

@Component("productService")
public class ProductServiceImpl implements IProductService {

	private static final String HQL_LIST_PROD_SHOP = "from Product where shop.id = :shopId "
			+ "and type = :type and logicalDelete.deleted = false";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(readOnly = true)
	@Override
	public MsgProductDTO getMsgProduct(long msgProductId) {
		MsgProduct prod = this.dao.get(MsgProduct.class, msgProductId);
		if (prod == null) {
			logger.debug("Can not find MsgProduct with ID:{}", msgProductId);
			return null;
		}
		return transferToDTO(prod);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MsgProductDTO> listAllMsgProduct() {
		List<MsgProduct> mps = this.dao.query("from MsgProduct where logicalDelete.deleted = false");
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
		dto.setUnitPrice((float) msgProduct.getUnitPrice() / 100);
		return dto;
	}

	private ProductDTO transferToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(product, dto);
		dto.setPicURL(product.getPic().getUrl());
		return dto;
	}
}
