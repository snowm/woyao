package com.woyao.customer.translator;

import org.springframework.beans.BeanUtils;

import com.woyao.customer.dto.ChatterDTO;
import com.woyao.customer.dto.OrderDTO;
import com.woyao.customer.dto.OrderItemDTO;
import com.woyao.domain.product.Product;
import com.woyao.domain.profile.ProfileWX;
import com.woyao.domain.purchase.Order;
import com.woyao.domain.purchase.OrderItem;

public class DefaultTranslator {

	public static Order translateToDomain(OrderDTO dto) {
		Order m = new Order();
		BeanUtils.copyProperties(dto, m, "id");
		ProfileWX consumer = new ProfileWX();
		consumer.setId(dto.getConsumer().getId());
		m.setConsumer(consumer);

		ProfileWX toProfile = new ProfileWX();
		toProfile.setId(dto.getToProfile().getId());
		m.setToProfile(toProfile);

		return m;
	}

	public static OrderItem translateToDomain(OrderItemDTO dto) {
		OrderItem m = new OrderItem();
		BeanUtils.copyProperties(dto, m, "id");
		Product product = new Product();
		product.setId(dto.getProductId());
		m.setProduct(product);
		
		return m;
	}

	public static OrderDTO translateToDTO(Order m) {
		OrderDTO dto = new OrderDTO();
		BeanUtils.copyProperties(m, dto);
		ProfileWX consumer = m.getConsumer();
		ProfileWX toProfile = m.getToProfile();
		dto.setCreationDate(m.getModification().getCreationDate());
		dto.setConsumer(translateToDTO(consumer));
		dto.setToProfile(translateToDTO(toProfile));

		return dto;
	}

	public static ChatterDTO translateToDTO(ProfileWX m) {
		ChatterDTO dto = new ChatterDTO();
		BeanUtils.copyProperties(m, dto);
		return dto;
	}

	public static OrderItemDTO translateToDTO(OrderItem m) {
		OrderItemDTO dto = new OrderItemDTO();
		BeanUtils.copyProperties(m, dto);
		dto.setProductId(m.getProduct().getId());
		dto.setProductName(m.getProduct().getName());
		dto.setProductPic(m.getProduct().getPic().getUrl());
		return dto;
	}
}
