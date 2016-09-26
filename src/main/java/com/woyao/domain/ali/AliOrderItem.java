package com.woyao.domain.ali;

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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.woyao.domain.product.Sku;
import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.snowm.hibernate.ext.usertype.ExtEnumType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ALI_ORDER_ITEM")
@TableGenerator(name = "aliOrderItemGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "aliOrderItem", allocationSize = 1, initialValue = 0)
public class AliOrderItem extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "aliOrderItemGenerator")
	private Long id;

	@Column(name = "TYPE", length = 2)
	@Type(type = ExtEnumType.CLASS_NAME, parameters = {
			@Parameter(name = ExtEnumType.PARA_ENUMCLASS, value = AliOrderItemType.CLASS_NAME) })
	private AliOrderItemType type;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "SKU_ID", nullable = false)
	private Sku sku;

	@Column(name = "QUANTITY", nullable = false)
	private int quantity;

	@Column(name = "TOTAL_PRICE", nullable = false)
	private float totalPrice;

	@Column(name = "FREIGHT", nullable = false)
	private float freight;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public AliOrderItemType getType() {
		return type;
	}

	public void setType(AliOrderItemType type) {
		this.type = type;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public float getFreight() {
		return freight;
	}

	public void setFreight(float freight) {
		this.freight = freight;
	}

}
