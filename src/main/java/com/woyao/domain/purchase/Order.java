package com.woyao.domain.purchase;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.snowm.hibernate.ext.usertype.ExtEnumType;
import com.woyao.domain.profile.ProfileWX;

@Entity
@Table(name = "ORDER")
@TableGenerator(name = "orderGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "order", allocationSize = 1, initialValue = 0)
public class Order extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderGenerator")
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "CONSUMER_ID")
	private ProfileWX consumer;

	@OneToOne(optional = false, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(referencedColumnName = "id", name = "PREPAY_INFO_ID", nullable = false)
	private OrderPrepayInfo prepayInfo;
	
	@OneToOne(optional = false, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(referencedColumnName = "id", name = "RESULT_INFO_ID")
	private OrderResultInfo resultInfo;

	@Column(name = "ORDER_STATUS", length = 2)
	@Type(type = ExtEnumType.CLASS_NAME, parameters = {
			@Parameter(name = ExtEnumType.PARA_ENUMCLASS, value = OrderStatus.CLASS_NAME) })
	private OrderStatus status;

	@Column(name = "TOTAL_FEE")
	private Integer totalFee;

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
