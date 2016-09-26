package com.woyao.mobile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.ShopDTO;
import com.woyao.dao.CommonDao;
import com.woyao.domain.Shop;

@Service("mobileService")
public class MobileService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
	public PaginationBean<ShopDTO> findShop(String name, long pageNumber, int pageSize) {
		List<Criterion> criterions = new ArrayList<>();

		if (!StringUtils.isBlank(name)) {
			String likeName = "%" + name + "%";
			criterions.add(Restrictions.like("name", likeName));
		}
		PaginationBean<ShopDTO> pb = new PaginationBean<>();
		pb.setPageNumber(pageNumber);
		pb.setPageSize(pageSize);
		long count = this.dao.count(Shop.class, criterions);
		pb.setTotalCount(count);
		if (count > 0) {
			List<Shop> result = this.dao.query(Shop.class, criterions, pageNumber, pageSize);
			List<ShopDTO> dtos = new ArrayList<>();
			for (Shop e : result) {
				ShopDTO dto = new ShopDTO();
				BeanUtils.copyProperties(e, dto);
				dtos.add(dto);
			}
			pb.setResults(dtos);
		}
		return pb;
	}

}
