package com.woyao.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.woyao.domain.Pic;
import com.woyao.domain.Shop;

@Entity
@Table(name = "PRODUCT")
@Inheritance(strategy = InheritanceType.JOINED)
@TableGenerator(name = "productGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "product", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "product")
public class Product extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "productGenerator")
	private Long id;

	@Column(name = "PRODUCT_TYPE", nullable = false)
	private ProductType type;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "SHOP_ID", nullable = true)
	private Shop shop;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	private String description;

	@Column(name = "UNIT_PRICE", nullable = false)
	private int unitPrice;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "PIC_ID", nullable = true)
	private Pic pic;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", type=" + type + ", shop=" + shop + ", name=" + name + ", description="
				+ description + ", unitPrice=" + unitPrice + ", pic=" + pic + "]";
	}
	
}
