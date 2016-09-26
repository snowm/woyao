package com.woyao.domain.ali;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.snowm.hibernate.ext.usertype.ExtEnumType;

@Entity
@Table(name = "ALI_ORDER")
@TableGenerator(name = "aliOrderGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "aliOrder", allocationSize = 1, initialValue = 0)
public class AliOrder extends DefaultModelImpl {

	private static final long serialVersionUID = -1427151975101702180L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "aliOrderGenerator")
	private Long id;

	@Column(name = "TB_ORDER_NO", length = 50, nullable = false, unique = true)
	private String tbOrderNo;

	@Column(name = "ORDER_STATUS", length = 2)
	@Type(type = ExtEnumType.CLASS_NAME, parameters = { @Parameter(name = ExtEnumType.PARA_ENUMCLASS, value = AliOrderStatus.CLASS_NAME) })
	private AliOrderStatus status;

	@Column(name = "ACCOUNT_VAL", length = 50, nullable = true)
	private String accountVal;

	@Column(name = "TOTAL_PRICE")
	private Integer totalPrice;

	@Column(name = "ALI_CREATION_DATE")
	private Date aliCreationDate;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
