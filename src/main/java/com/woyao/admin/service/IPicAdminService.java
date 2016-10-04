package com.woyao.admin.service;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.PicDTO;
import com.woyao.admin.dto.product.QueryPicRequestDTO;
import com.woyao.domain.Pic;

public interface IPicAdminService extends IAdminService<Pic, PicDTO>{
	PaginationBean<PicDTO> query(QueryPicRequestDTO request);
}
