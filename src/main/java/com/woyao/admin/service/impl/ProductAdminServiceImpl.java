package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.woyao.dao.CommonDao;
import com.woyao.domain.Pic;
import com.woyao.domain.Shop;
import com.woyao.domain.product.MsgProduct;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductType;

@Service("productAdminService")
public class ProductAdminServiceImpl extends AbstractAdminService<Product, ProductDTO> implements IProductAdminService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ProductDTO update(ProductDTO dto) {
		Product m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);

		return dto;
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

}
