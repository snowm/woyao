package com.woyao.customer.service;

import com.woyao.wx.dto.UnifiedOrderRequestDTO;
import com.woyao.wx.dto.UnifiedOrderResponse;

public interface IWxAdminService {
	public UnifiedOrderRequestDTO getUnifiedDTO(String productId);
	
	public void svaeOrder(UnifiedOrderRequestDTO dto,UnifiedOrderResponse orderResponse);
	
	public void savePrePayId(UnifiedOrderResponse orderResponse,String OpenId);
}