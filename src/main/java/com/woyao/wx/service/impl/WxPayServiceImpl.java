package com.woyao.wx.service.impl;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woyao.GlobalConfig;
import com.woyao.dao.CommonDao;
import com.woyao.domain.product.Product;
import com.woyao.domain.profile.ProfileWX;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderItem;
import com.woyao.domain.purchase.OrderPrepayInfo;
import com.woyao.domain.purchase.OrderResultInfo;
import com.woyao.domain.purchase.OrderStatus;
import com.woyao.wx.dto.ProductDetail;
import com.woyao.wx.dto.UnifiedOrderRequestDTO;
import com.woyao.wx.dto.UnifiedOrderResponse;
import com.woyao.wx.service.IWxPayService;

@Service("wxAdminService")
@Transactional
public class WxPayServiceImpl implements IWxPayService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	public UnifiedOrderRequestDTO getUnifiedDTO(String productId, Long quantity) {
		UnifiedOrderRequestDTO dto = new UnifiedOrderRequestDTO();
		dto.setAppid(globalConfig.getAppId());
		dto.setMchId(globalConfig.getMrchId());
		dto.setDeviceInfo("1234567890");
		dto.setNonceStr(getRandomStringByLength(16));
		/**
		 * 签名位置
		 */
		ProductDetail productDetail = getProductDTO(productId, quantity);
		dto.setBody("JSAPI支付测试");
		dto.setDetail(productDetail.toString());
		dto.setAttach("支付测试");
		dto.setOutTradeNo((new Date()).toString());
		dto.setFeeType("CNY");
		dto.setTotalFee(productDetail.getQuantity() * productDetail.getPrice());
		dto.setSpbillCreateIp("127.0.0.1");
		dto.setNotifyUrl("http://localhost:8080//m/wxPay/");
		dto.setTradeType("JSAPI");
		dto.setOpenid("openId");
		return dto;
	}

	/**
	 * 获取随机数
	 */
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

	private ProductDetail getProductDTO(String productId, Long quantity) {
		Long product = Long.parseLong(productId);
		System.out.println(product);
		ProductDetail dto = new ProductDetail();
		Product m = this.dao.get(Product.class, product);
		if (m != null) {
			dto.setBody(m.getDescription());
			dto.setGoods_name(m.getName());
			dto.setGoods_id(m.getId().toString());
			dto.setPrice((int) m.getUnitPrice());
			dto.setQuantity(quantity.intValue());
		}
		return dto;
	}

	/**
	 * 保存订单及其所有的关联数据
	 */
	public void svaeOrder(UnifiedOrderRequestDTO dto, UnifiedOrderResponse orderResponse, String productId, Long quantity) {
		if (!dto.getOpenid().isEmpty()) {
			String openId = dto.getOpenid();
			Order o = new Order();
			OrderItem item = new OrderItem();
			ProfileWX p = this.dao.queryUnique("from ProfileWX where openId=" + openId);
			OrderPrepayInfo or = new OrderPrepayInfo();
			or.setPrepayId(orderResponse.getPrepayId());
			o.setPrepayInfo(or);
			o.setConsumer(p);
			o.setToProfile(p);
			if ("SUCCESS".equals(orderResponse.getResultCode())) {
				o.setStatus(OrderStatus.SUCCESS);
			} else {
				o.setStatus(OrderStatus.FAIL);
			}
			o.setTotalFee(dto.getTotalFee());
			OrderResultInfo orr = this.dao.queryUnique("from OrderResultInfo where openId=" + openId);
			o.setResultInfo(orr);
			item.setOrder(o);
			item.setTotalFee(dto.getTotalFee());
			Product product = this.dao.get(Product.class, productId);
			item.setUnitPrice(product.getUnitPrice());
			item.setQuantity(quantity.intValue());
			this.dao.save(o);
			this.dao.save(item);
		}
	}

	/**
	 * 保存预付款Id,保存结果状态码
	 */
	public void savePrePayId(UnifiedOrderResponse orderResponse, String OpenId) {
		if (!orderResponse.getPrepayId().isEmpty()) {
			OrderPrepayInfo or = new OrderPrepayInfo();
			or.setPrepayId(orderResponse.getPrepayId());
			this.dao.save(or);
		}
		if (!orderResponse.getResultCode().isEmpty()) {
			OrderResultInfo or = new OrderResultInfo();
			or.setReturnCode(orderResponse.getResultCode());
			or.setOpenId(OpenId);
			this.dao.save(or);
		}
	}
}
