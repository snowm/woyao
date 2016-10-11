package com.woyao.customer.service;

import com.woyao.wx.dto.UnifiedOrderRequestDTO;

public interface IWxAdminService {
	public UnifiedOrderRequestDTO getUnifiedDTO(String productId);
}