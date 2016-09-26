package com.woyao.domain.ali;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "ALI_IN_TRANSIT_ORDER_ITEM")
public class AliInTransitOrderItem extends AliOrderItem {

	private static final long serialVersionUID = 1L;
	
//	private 

}
