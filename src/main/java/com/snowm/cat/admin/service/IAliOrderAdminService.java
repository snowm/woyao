package com.snowm.cat.admin.service;

import com.snowm.cat.admin.dto.ali.AliOrderDTO;
import com.snowm.cat.admin.dto.ali.QueryAliOrdersRequestDTO;
import com.snowm.cat.domain.ali.AliOrder;
import com.snowm.utils.query.PaginationBean;

public interface IAliOrderAdminService extends IAdminService<AliOrder, AliOrderDTO> {

	PaginationBean<AliOrderDTO> query(QueryAliOrdersRequestDTO queryRequest);

	AliOrderDTO transferToSimpleDTO(AliOrder m);

}
