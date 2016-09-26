package com.woyao.domain.purchase;

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
public class PurchaseOrderItem extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "aliOrderItemGenerator")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "SKU_ID", nullable = false)
	private Sku sku;

	@Column(name = "TOTAL_PRICE", nullable = false)
	private float officialPrice;

	@Column(name = "FREIGHT", nullable = false)
	private float freight;

	@Column(name = "ORDER_STATUS", length = 2)
	@Type(type = ExtEnumType.CLASS_NAME, parameters = {
			@Parameter(name = ExtEnumType.PARA_ENUMCLASS, value = PurchaseOrderStatus.CLASS_NAME) })
	private PurchaseOrderStatus status;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public float getOfficialPrice() {
		return officialPrice;
	}

	public void setOfficialPrice(float officialPrice) {
		this.officialPrice = officialPrice;
	}

	public float getFreight() {
		return freight;
	}

	public void setFreight(float freight) {
		this.freight = freight;
	}

	public PurchaseOrderStatus getStatus() {
		return status;
	}

	public void setStatus(PurchaseOrderStatus status) {
		this.status = status;
	}

}
