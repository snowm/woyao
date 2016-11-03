package com.woyao.customer.service;

import java.util.List;

import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;

public interface IProductService {

	MsgProductDTO getMsgProduct(long shopId, long msgProductId);

	List<MsgProductDTO> listAllMsgProductFromCache(long shopId);
	
	List<MsgProductDTO> listAllGlobalMsgProduct();

	List<ProductDTO> listProducts(long shopId);

}
