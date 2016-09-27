package com.woyao.domain.purchase;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Table(name = "ORDER_RESULT_INFO")
@TableGenerator(name = "orderResultInfoGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "orderResultInfo", allocationSize = 1, initialValue = 0)
public class OrderResultInfo extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderResultInfoGenerator")
	private Long id;

	@Column(name = "OPEN_ID", length = 50, nullable=false)
	private String openId;

	@Column(name = "RETURN_CODE", length = 10, nullable=false)
	private String returnCode;

	@Column(name = "COMPLETE_DATE", length = 10, nullable=false)
	private Date completeDate;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
