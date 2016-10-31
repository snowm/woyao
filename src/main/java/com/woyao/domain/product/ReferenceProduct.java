package com.woyao.domain.product;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "REF_PRODUCT")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "refProduct")
public class ReferenceProduct extends Product {

	private static final long serialVersionUID = 1L;

	@Column(name = "REF_PRODUCT_ID", nullable = false)
	private long referencedProductId;

	public long getReferencedProductId() {
		return referencedProductId;
	}

	public void setReferencedProductId(long referencedProductId) {
		this.referencedProductId = referencedProductId;
	}

}
