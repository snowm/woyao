package com.woyao.admin.service;

import com.woyao.admin.dto.ali.AliOrderDTO;
import com.woyao.admin.dto.ali.QueryAliOrdersRequestDTO;
import com.woyao.domain.ali.AliOrder;
import com.snowm.utils.query.PaginationBean;

public interface IAliOrderAdminService extends IAdminService<AliOrder, AliOrderDTO> {

	PaginationBean<AliOrderDTO> query(QueryAliOrdersRequestDTO queryRequest);

	AliOrderDTO transferToSimpleDTO(AliOrder m);

}
