package com.woyao.admin.shop.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.security.web.exception.NotFoundException;
import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.controller.AbstractBaseController;
import com.woyao.admin.dto.product.ProductDTO;
import com.woyao.admin.dto.product.QueryProductsRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IProductAdminService;
import com.woyao.cache.MsgProductCache;
import com.woyao.customer.chat.SessionUtils;
import com.woyao.domain.product.Product;
import com.woyao.domain.product.ProductType;

@Controller("shopAdminProductController")
@RequestMapping(value = "/shop/admin/product")
public class ShopProductController extends AbstractBaseController<Product, ProductDTO> {

	@Autowired
	private IProductAdminService service;

	@Resource(name = "msgProductCache")
	private MsgProductCache msgProductCache;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProductDTO> query(QueryProductsRequestDTO request, HttpServletRequest httpRequest) {
		long shopId = SessionUtils.getShopId(httpRequest.getSession());
		request.setShopId(shopId);
		PaginationBean<ProductDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "/search/msg" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<ProductDTO> queryMsg(HttpServletRequest httpRequest) {
		long shopId = SessionUtils.getShopId(httpRequest.getSession());
		List<ProductDTO> results = service.listShopMsgProducts(shopId);
		PaginationBean<ProductDTO> pb = new PaginationBean<>();
		pb.setPageNumber(1);
		pb.setTotalCount(results.size());
		pb.setResults(results);
		pb.setPageSize(100);
		return pb;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProductDTO saveOrUpdate(ProductDTO dto, HttpServletRequest httpRequest) {
		if (dto.getId() != null) {
			ProductDTO existed = this.service.get(dto.getId());
			if (existed != null) {
				if (existed.getTypeId() != ProductType.MSG.getTypeValue()) {
					this.verifyShopProduct(existed, httpRequest);
				} else {
					dto.setShopId(SessionUtils.getShopId(httpRequest.getSession()));
					this.service.updateShopMsgProduct(dto);
					return dto;
				}
			} else {
				throw new IllegalArgumentException("Product with id:" + dto.getId() + " does not exist!");
			}
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}

	@RequestMapping(value = { "/enabled/{id}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProductDTO enable(@PathVariable("id") long id, HttpServletRequest httpRequest) {
		this.verifyShopProduct(id, httpRequest);
		ProductDTO dto = this.baseService.enable(id);
		if (dto == null) {
			throw new NotFoundException(String.format("%1$s with id : %2$s cound not found!", Product.class.getName(), id));
		}
		return dto;
	}

	@RequestMapping(value = { "/disable/{id}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProductDTO disable(@PathVariable("id") long id, HttpServletRequest httpRequest) {
		this.verifyShopProduct(id, httpRequest);
		ProductDTO dto = this.baseService.disable(id);
		if (dto == null) {
			throw new NotFoundException(String.format("%1$s with id : %2$s cound not found!", Product.class.getName(), id));
		}
		return dto;
	}

	@RequestMapping(value = { "/delete/{id}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ProductDTO delete(@PathVariable("id") long id, HttpServletRequest httpRequest) {
		this.verifyShopProduct(id, httpRequest);
		ProductDTO dto = this.baseService.delete(id);
		if (dto == null) {
			throw new NotFoundException(String.format("%1$s with id : %2$s cound not found!", Product.class.getName(), id));
		}
		return dto;
	}

	@Autowired
	@Override
	public void setBaseService(@Qualifier("productAdminService") IAdminService<Product, ProductDTO> baseService) {
		this.baseService = baseService;
	}

	private void verifyShopProduct(long productId, HttpServletRequest httpRequest) {
		ProductDTO dto = this.service.get(productId);
		this.verifyShopProduct(dto, httpRequest);
	}

	private void verifyShopProduct(ProductDTO dto, HttpServletRequest httpRequest) {
		long shopId = SessionUtils.getShopId(httpRequest.getSession());
		Assert.isTrue(dto.getShopId() != null && dto.getShopId() == shopId, "这不是当前店铺的产品！");
	}

}
