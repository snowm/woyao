package com.woyao.customer.service.impl;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woyao.GlobalConfig;
import com.woyao.customer.service.IWxAdminService;
import com.woyao.dao.CommonDao;
import com.woyao.domain.product.Product;
import com.woyao.domain.profile.ProfileWX;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderPrepayInfo;
import com.woyao.domain.purchase.OrderResultInfo;
import com.woyao.domain.purchase.OrderStatus;
import com.woyao.wx.dto.ProductDetail;
import com.woyao.wx.dto.UnifiedOrderRequestDTO;
import com.woyao.wx.dto.UnifiedOrderResponse;


@Service("wxAdminService")
@Transactional
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
		dto.setNonceStr(getRandomStringByLength(16));
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
		dto.setOpenid("openId");
		return dto;
	}
	
	/**
	 * 获取随机数
	 * */
	private static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
	
	private ProductDetail getProductDTO(String productId){
		Long product=Long.parseLong(productId);
		System.out.println(product);
		ProductDetail dto=new ProductDetail();
		Product m=this.dao.get(Product.class, product);
		if(m!=null){
			dto.setBody(m.getDescription());
			dto.setGoods_name(m.getName());
			dto.setGoods_id(m.getId().toString());
			dto.setPrice((int)m.getUnitPrice());
			dto.setQuantity(1);
		}
		return dto;
	}
	
	/**
	 * 保存订单及其所有的关联数据
	 */
	public void svaeOrder(UnifiedOrderRequestDTO dto,UnifiedOrderResponse orderResponse){
		if(!dto.getOpenid().isEmpty()){
			String openId=dto.getOpenid();		
			Order o=new Order();
			ProfileWX p=this.dao.queryUnique("from ProfileWX where openId="+openId);			
			OrderPrepayInfo or=new OrderPrepayInfo();
			or.setPrepayId(orderResponse.getPrepayId());
			o.setPrepayInfo(or);
			o.setConsumer(p);
			o.setToProfile(p);
			if("SUCCESS".equals(orderResponse.getResultCode())){				
				o.setStatus(OrderStatus.SUCCESS);
			}else{
				o.setStatus(OrderStatus.FAIL);
			}
			o.setTotalFee(dto.getTotalFee());			
			OrderResultInfo orr=this.dao.queryUnique("from OrderResultInfo where openId="+openId);
			o.setResultInfo(orr);
		}
	}
	
	/**
	 * 保存预付款Id,保存结果状态码
	 */
	public void savePrePayId(UnifiedOrderResponse orderResponse,String OpenId){
		if(!orderResponse.getPrepayId().isEmpty()){
			OrderPrepayInfo or=new OrderPrepayInfo();
			or.setPrepayId(orderResponse.getPrepayId());		
			this.dao.save(or);
		}
		if(!orderResponse.getResultCode().isEmpty()){
			OrderResultInfo or=new OrderResultInfo();
			or.setReturnCode(orderResponse.getResultCode());
			or.setOpenId(OpenId);
			this.dao.save(or);
		}
	}
}
