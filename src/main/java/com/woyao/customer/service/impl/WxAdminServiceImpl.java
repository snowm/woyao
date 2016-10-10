package com.woyao.customer.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.woyao.GlobalConfig;
import com.woyao.customer.service.IWxAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.product.Product;
import com.woyao.wx.dto.ProductDetail;
import com.woyao.wx.dto.UnifiedOrderRequestDTO;


@Service("wxAdminService")
public class WxAdminServiceImpl implements IWxAdminService{

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;
	
	public UnifiedOrderRequestDTO getUnifiedDTO(String productId){
		UnifiedOrderRequestDTO dto=new UnifiedOrderRequestDTO();
		dto.setAppid(globalConfig.getAppId());
		dto.setMchId(globalConfig.getMrchId());
		dto.setDeviceInfo("1234567890");
		dto.setNonceStr(getNonceStr());
		/**
		 * 签名位置
		 * */
		ProductDetail productDetail=getProductDTO(productId);
		dto.setBody("JSAPI支付测试");
		dto.setDetail(productDetail.toString());
		dto.setAttach("支付测试");
		dto.setOutTradeNo((new Date()).toString());
		dto.setFeeType("CNY");
		dto.setTotalFee(1);
		dto.setSpbillCreateIp("127.0.0.1");
		dto.setNotifyUrl("http://localhost:8080//m/wxPay/");
		dto.setTradeType("JSAPI");
		return dto;
	}
	public static String getNonceStr() {
		String uuid = UUID.randomUUID().toString(); 
		uuid = uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24); 
		        return uuid;
	}
	
	public ProductDetail getProductDTO(String productId){
		ProductDetail dto=new ProductDetail();
		Product m=this.dao.get(Product.class, productId);
		if(m!=null){
			dto.setBody(m.getDescription());
			dto.setGoods_name(m.getName());
			dto.setGoods_id(m.getId().toString());
			dto.setPrice((int)m.getUnitPrice());
			dto.setQuantity(1);
		}
		return dto;
	}
	
}
