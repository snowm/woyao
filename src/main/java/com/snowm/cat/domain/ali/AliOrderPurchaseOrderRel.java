package com.snowm.cat.domain.ali;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ALI_ORDER_PURCHASE_ORDER_REL")
@TableGenerator(name = "aliOrderPurchaseOrderRelGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "aliOrderPurchaseOrderRel", allocationSize = 1, initialValue = 0)
public class AliOrderPurchaseOrderRel extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "aliOrderPurchaseOrderRelGenerator")
	private Long id;
	
	private AliOrder aliOrder;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
