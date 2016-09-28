package com.woyao.domain.purchase;

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
import javax.persistence.Version;

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

	@Version
	@Column(name = "VERSION")
	private int version = 0;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "CONSUMER_ID", nullable = false)
	private ProfileWX consumer;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "TO_PROFILE_WX_ID", nullable = true)
	private ProfileWX toProfile;

	@OneToOne(optional = false, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(referencedColumnName = "id", name = "PREPAY_INFO_ID", nullable = false)
	private OrderPrepayInfo prepayInfo;

	@OneToOne(optional = false, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(referencedColumnName = "id", name = "RESULT_INFO_ID")
	private OrderResultInfo resultInfo;

	@Column(name = "ORDER_STATUS", length = 2)
	@Type(type = ExtEnumType.CLASS_NAME, parameters = { @Parameter(name = ExtEnumType.PARA_ENUMCLASS, value = OrderStatus.CLASS_NAME) })
	private OrderStatus status;

	@Column(name = "TOTAL_FEE")
	private Integer totalFee;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
