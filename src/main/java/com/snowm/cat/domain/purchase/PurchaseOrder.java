package com.snowm.cat.domain.purchase;

import java.util.Date;

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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.snowm.hibernate.ext.usertype.ExtEnumType;

@Entity
@Table(name = "PURCHASE_ORDER")
@TableGenerator(name = "purchaseOrderGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "purchaseOrder", allocationSize = 1, initialValue = 0)
public class PurchaseOrder extends DefaultModelImpl {

	private static final long serialVersionUID = -1427151975101702180L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "purchaseOrderGenerator")
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "ACCOUNT_ID", nullable = false)
	private PurchaseAccount account;

	@Column(name = "TB_ORDER_NO", length = 50, nullable = false, unique = true)
	private String tbOrderNo;

	@Column(name = "ORDER_STATUS", length = 2)
	@Type(type = ExtEnumType.CLASS_NAME, parameters = { @Parameter(name = ExtEnumType.PARA_ENUMCLASS, value = PurchaseOrderStatus.CLASS_NAME) })
	private PurchaseOrderStatus status;

	@Column(name = "TOTAL_PRICE")
	private Integer totalPrice;

	@Column(name = "PURCHASE_DATE")
	private Date purchaseDate;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
