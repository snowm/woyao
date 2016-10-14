package com.woyao.customer;

import com.woyao.customer.dto.OrderDTO;

public interface OrderProcessor {

	boolean process(OrderDTO order);
	
}
