package com.woyao.domain.purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Table(name = "ORDER_PREPAY_INFO")
@TableGenerator(name = "orderPrepayInfoGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "orderPrepayInfo", allocationSize = 1, initialValue = 0)
public class OrderPrepayInfo extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderPrepayInfoGenerator")
	private Long id;
	
	@Column(name = "PRE_PAY_ID", length = 64, nullable = false)
	private String prepayId;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}


}
