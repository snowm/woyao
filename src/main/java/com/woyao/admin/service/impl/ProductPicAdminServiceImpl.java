package com.woyao.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.ProductPicDTO;
import com.woyao.admin.dto.product.QueryProductPicRequestDTO;
import com.woyao.admin.service.IProductPicAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductPic;

@Service("productPicAdminService")
public class ProductPicAdminServiceImpl extends AbstractAdminService<ProductPic, ProductPicDTO> implements IProductPicAdminService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	@Override
	public ProductPicDTO update(ProductPicDTO dto) {
		ProductPic m = this.transferToDomain(dto);
		dao.saveOrUpdate(m);
		return this.transferToFullDTO(m);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_UNCOMMITTED)
	@Override
	public PaginationBean<ProductPicDTO> query(QueryProductPicRequestDTO request) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (request.getPicId()!=null) {
			criterions.add(Restrictions.eq("picId", request.getPicId()));
		}
		if (request.getProductId()!=null) {
			criterions.add(Restrictions.eq("productId", request.getProductId()));
		}
		List<Order> orders = new ArrayList<>();
		orders.add(Order.desc("id"));

		long count = this.dao.count(this.entityClazz, criterions);
		List<ProductPic> ms = new ArrayList<>();
		if (count > 0l) {
			ms = this.dao.query(this.entityClazz, criterions, orders, request.getPageNumber(), request.getPageSize());
		}

		PaginationBean<ProductPicDTO> rs = new PaginationBean<>(request.getPageNumber(), request.getPageSize());
		rs.setTotalCount(count);
		List<ProductPicDTO> results = new ArrayList<>();
		for (ProductPic m : ms) {
			ProductPicDTO dto = this.transferToDTO(m, false);
			results.add(dto);
		}
		rs.setResults(results);
		return rs;
	}

	@Override
	public ProductPic transferToDomain(ProductPicDTO dto) {
		ProductPic m=new ProductPic();
		BeanUtils.copyProperties(dto, m);
		m.getLogicalDelete().setEnabled(dto.isEnabled());
		m.getLogicalDelete().setDeleted(dto.isDeleted());
		return m;
	}

	@Override
	public ProductPicDTO transferToSimpleDTO(ProductPic m) {
		ProductPicDTO dto=new ProductPicDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setPicId(m.getPic().getId());
		dto.setPicUrl(m.getPic().getUrl());
		dto.setProductId(m.getProductId());
		dto.setProductName(dao.get(Product.class, m.getProductId()).getName());
		dto.setEnabled(m.getLogicalDelete().isEnabled());
		dto.setDeleted(m.getLogicalDelete().isDeleted());
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setLastModifiedDate(m.getModification().getLastModifiedDate());
		return dto;
	}

	@Override
	public ProductPicDTO transferToFullDTO(ProductPic m) {
		
		return transferToSimpleDTO(m);
	}
}
