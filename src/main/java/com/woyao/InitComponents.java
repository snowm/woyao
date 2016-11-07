package com.woyao;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.woyao.cache.RicherReportCache;
import com.woyao.customer.service.IOrderService;
import com.woyao.customer.service.IProductService;

@Component("initComponents")
public class InitComponents implements InitializingBean {

	@Resource(name = "productService")
	private IProductService productService;

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "richerReportCache")
	private RicherReportCache richerReportCache;

	@Override
	public void afterPropertiesSet() throws Exception {
		productService.loadMsgProductCache();
		this.loadShopDailyRichers();
	}

	/**
	 * 加载店铺的日土豪榜统计数据
	 */
	private void loadShopDailyRichers() {
		Map<Long, Map<Long, Integer>> richers = orderService.shopDailyMsgOrderConsumerReport();
		richers.forEach((k, v) -> {
			richerReportCache.loadReport(k, v);
		});
	}
}
