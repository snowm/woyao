package com.woyao.customer.service;

import com.woyao.customer.dto.ChatterDTO;

public interface IProfileWxService {

	ChatterDTO getByOpenId(String openId);

	ChatterDTO saveChatterInfo(ChatterDTO dto);

	ChatterDTO getById(long id);

}
