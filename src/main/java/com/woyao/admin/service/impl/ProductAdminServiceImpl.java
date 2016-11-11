package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryProductsRequestDTO;
import com.woyao.admin.service.IProductAdminService;
import com.woyao.cache.MsgProductCache;
import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Pic;
import com.woyao.domain.Shop;
import com.woyao.domain.product.MsgProduct;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductType;
import com.woyao.domain.product.ShopMsgProductConfig;

@Service("productAdminService")
public class ProductAdminServiceImpl extends AbstractAdminService<Product, ProductDTO> implements IProductAdminService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "msgProductCache")
	private MsgProductCache msgProductCache;

	@Transactional
	@Override
	public ProductDTO create(ProductDTO dto) {
		Product m = this.transferToDomain(dto);
		this.dao.save(m);
		
		ProductDTO rs = this.get(m.getId());
		if (m.getType() == ProductType.MSG) {
			MsgProduct msgProduct = (MsgProduct) m;
			this.msgProductCache.updateGlobalMsgProduct(transferToCacheDTO(msgProduct));
		}
		return rs;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public ProductDTO update(ProductDTO dto) {
		Product m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		
		if (m.getType() == ProductType.MSG) {
			MsgProduct msgProduct = (MsgProduct) m;
			this.msgProductCache.updateGlobalMsgProduct(transferToCacheDTO(msgProduct));
		}
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<ProductDTO> listGlobalMsgProducts() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("type", ProductType.MSG);
		List<MsgProduct> mps = this.dao.query("from MsgProduct where shop.id is null and type = :type and logicalDelete.deleted = false",
				paramMap);
		List<ProductDTO> rs = new ArrayList<>();
		if (mps == null || mps.isEmpty()) {
			return rs;
		}
		for (MsgProduct mp : mps) {
			rs.add(transferToDTO(mp, false));
		}
		return rs;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<ProductDTO> listShopMsgProducts(long shopId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("shopId", shopId);
		List<ShopMsgProductConfig> mps = this.dao.query("from ShopMsgProductConfig where shopId = :shopId", paramMap);
		List<ProductDTO> rs = listGlobalMsgProducts();
		if (mps == null || mps.isEmpty()) {
			return rs;
		}
		for (ShopMsgProductConfig mp : mps) {
			for (ProductDTO p : rs) {
				if (p.getId().equals(mp.getReferencedProductId())) {
					p.setUnitPrice(mp.getUnitPrice());
				}
			}
		}
		return rs;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public void updateShopMsgProduct(ProductDTO dto) {
		long msgProductId = dto.getId();
		MsgProduct msgProduct = this.dao.get(MsgProduct.class, msgProductId);
		if (msgProduct == null) {
			throw new IllegalArgumentException("Product with id:" + msgProductId + " does not exist!");
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", msgProductId);
		Long shopId = dto.getShopId();
		paramMap.put("shopId", shopId);
		ShopMsgProductConfig conf = this.dao
				.queryUnique("from ShopMsgProductConfig where referencedProductId = :productId and shopId = :shopId", paramMap);
		if (conf == null) {
			conf = new ShopMsgProductConfig();
		}
		conf.setReferencedProductId(dto.getId());
		conf.setUnitPrice(dto.getUnitPrice());
		conf.setShopId(shopId);
		this.dao.saveOrUpdate(conf);

		MsgProduct shopMsgProduct = new MsgProduct();
		BeanUtils.copyProperties(msgProduct, shopMsgProduct);
		Shop shop = new Shop();
		shop.setId(shopId);
		shopMsgProduct.setShop(shop);
		shopMsgProduct.setUnitPrice(conf.getUnitPrice());
		this.msgProductCache.updateShopMsgProduct(shopId, transferToCacheDTO(shopMsgProduct));
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	public PaginationBean<ProductDTO> query(QueryProductsRequestDTO request) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!StringUtils.isEmpty(request.getName())) {
			criterions.add(Restrictions.like("name", "%" + request.getName() + "%"));
		}
		if (request.getShopId() != null) {
			criterions.add(Restrictions.eq("shop.id", request.getShopId()));
		}
		if (request.getType() != null) {
			criterions.add(Restrictions.eq("type", request.getType()));
		}
		if (request.getDeleted() != null) {
			criterions.add(Restrictions.eq("logicalDelete.deleted", request.getDeleted()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<Product> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}
		PaginationBean<ProductDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<ProductDTO> results = new ArrayList<>();
		for (Product m : ms) {
			ProductDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Transactional
	@Override
	public ProductDTO delete(long id) {
		Product m = this.dao.get(Product.class, id);
		if (m == null) {
			return null;
		}
		m.getLogicalDelete().setDeleted(true);
		if (m.getType() == ProductType.MSG) {
			this.msgProductCache.deleteGlobalMsgProduct(id);
		}
		return this.transferToDTO(m, false);
	}

	@Override
	public Product transferToDomain(ProductDTO dto) {
		Product m = null;
		if (dto.getTypeId() == ProductType.MSG.getTypeValue()) {
			m = new MsgProduct();
		} else {
			m = new Product();
		}
		BeanUtils.copyProperties(dto, m);
		m.setType(ProductType.getEnum(dto.getTypeId()));
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		if (dto.getShopId() != null) {
			Shop shop = new Shop();
			shop.setId(dto.getShopId());
			m.setShop(shop);
		}
		if (dto.getMainPicId() != null) {
			Pic pic = new Pic();
			pic.setId(dto.getMainPicId());
			m.setPic(pic);
		}
		return m;
	}

	@Override
	public ProductDTO transferToSimpleDTO(Product m) {
		ProductDTO dto = new ProductDTO();
		BeanUtils.copyProperties(m, dto);

		if (m.getShop() != null) {
			dto.setShopId(m.getShop().getId());
			dto.setShopName(m.getShop().getName());
		}
		dto.setTypeId(m.getType().getPersistedValue());
		if (m.getPic() != null) {
			dto.setMainPic(m.getPic().getUrl());
			dto.setMainPicId(m.getPic().getId());
		}
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public ProductDTO transferToFullDTO(Product m) {
		return transferToSimpleDTO(m);
	}

	private MsgProductDTO transferToCacheDTO(MsgProduct msgProduct) {
		MsgProductDTO dto = new MsgProductDTO();
		BeanUtils.copyProperties(msgProduct, dto);
		if (msgProduct.getPic() != null) {
			dto.setPicURL(msgProduct.getPic().getUrl());
		}
		dto.setUnitPrice((float) msgProduct.getUnitPrice() / 100);
		dto.setUnitPriceCent(msgProduct.getUnitPrice());
		return dto;
	}

}
