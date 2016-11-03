package com.woyao.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Table(name = "SHOP_MSG_PRODUCT_CONF")
@TableGenerator(name = "shopMsgProductConfigGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "shopMsgProductConfig", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "shopMsgProductConfig")
public class ShopMsgProductConfig extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "shopMsgProductConfigGenerator")
	private Long id;

	@Column(name = "SHOP_ID")
	private long shopId;

	@Column(name = "REF_PRODUCT_ID", nullable = false)
	private long referencedProductId;

	@Column(name = "UNIT_PRICE", nullable = false)
	private int unitPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public long getReferencedProductId() {
		return referencedProductId;
	}

	public void setReferencedProductId(long referencedProductId) {
		this.referencedProductId = referencedProductId;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

}
