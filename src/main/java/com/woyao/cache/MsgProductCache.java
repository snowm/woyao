package com.woyao.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.woyao.customer.dto.MsgProductDTO;

@Component("msgProductCache")
public class MsgProductCache {

	private Map<Long, MsgProductDTO> globalMsgProducts = new ConcurrentHashMap<>();

	private Map<Long, Map<Long, MsgProductDTO>> shopMsgProducts = new ConcurrentHashMap<>();

	public Map<Long, MsgProductDTO> getGlobalMsgProducts() {
		return globalMsgProducts;
	}

	public void updateGlobalMsgProduct(MsgProductDTO product) {
		Long productId = product.getId();
		this.globalMsgProducts.put(productId, product);
		this.shopMsgProducts.forEach((k, v) -> {
			MsgProductDTO shopProduct = v.computeIfAbsent(productId, v1 -> {
				MsgProductDTO tmpProd = new MsgProductDTO();
				BeanUtils.copyProperties(product, tmpProd);
				return tmpProd;
			});
			BeanUtils.copyProperties(product, shopProduct, "");
		});
	}

	public void addShop(long shopId) {
		Map<Long, MsgProductDTO> shopProducts = this.shopMsgProducts.computeIfAbsent(shopId, v -> new ConcurrentHashMap<>());
		this.globalMsgProducts.forEach((k, v) -> {
			MsgProductDTO tmpProd = new MsgProductDTO();
			BeanUtils.copyProperties(v, tmpProd);
			shopProducts.put(k, tmpProd);
		});
	}

	public void deleteShop(long shopId) {
		this.shopMsgProducts.remove(shopId);
	}

	public void updateShopMsgProduct(long shopId, MsgProductDTO product) {
		Map<Long, MsgProductDTO> shopProducts = this.shopMsgProducts.computeIfAbsent(shopId, v -> new ConcurrentHashMap<>());
		shopProducts.put(product.getId(), product);
	}

	public MsgProductDTO getGlobalMsgProduct(long productId) {
		return this.globalMsgProducts.get(productId);
	}

	public Map<Long, MsgProductDTO> getShopMsgProducts(long shopId) {
		return this.shopMsgProducts.get(shopId);
	}
	
	public MsgProductDTO getShopMsgProduct(long shopId, long productId) {
		Map<Long, MsgProductDTO> shopProducts = this.shopMsgProducts.get(shopId);
		if (shopProducts == null) {
			return null;
		}
		return shopProducts.get(productId);
	}

	public void deleteGlobalMsgProduct(long productId) {
		this.globalMsgProducts.remove(productId);
		this.shopMsgProducts.forEach((k, v) -> {
			v.remove(productId);
		});
	}
}
