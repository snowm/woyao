package com.woyao.domain.purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.woyao.domain.product.Product;

@Entity
@Table(name = "ORDER_ITEM")
@TableGenerator(name = "orderItemGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "orderItem", allocationSize = 1, initialValue = 0)
public class OrderItem extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderItemGenerator")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "ORDER_ID", nullable = false)
	private Order order;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "PRODUCT_ID", nullable = false)
	private Product product;

	@Column(name = "UNIT_PRICE", nullable = false)
	private int unitPrice;

	@Column(name = "QUANTITY", nullable = false)
	private int quantity = 1;

	@Column(name = "TOTAL_FEE", nullable = false)
	private int totalFee;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public void calcTotalFee() {
		this.totalFee = this.unitPrice * this.quantity;
	}

}
