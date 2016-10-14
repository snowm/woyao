package com.woyao.wx.service;

import com.woyao.customer.dto.OrderDTO;
import com.woyao.wx.dto.UnifiedOrderResponse;

public interface IWxPayService {
	
	UnifiedOrderResponse unifiedOrder(OrderDTO order);
	
}