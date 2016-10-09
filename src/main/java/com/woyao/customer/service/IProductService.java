package com.woyao.customer.service;

import java.util.List;

import com.woyao.customer.dto.MsgProductDTO;
import com.woyao.customer.dto.ProductDTO;

public interface IProductService {

	MsgProductDTO getMsgProduct(long msgProductId);

	List<MsgProductDTO> listAllMsgProduct();

	List<ProductDTO> listProducts(long shopId);

}
