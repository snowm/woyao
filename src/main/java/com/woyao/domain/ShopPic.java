package com.woyao.domain;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.woyao.domain.Pic;

@Entity
@Table(name = "SHOP_PICTURE")
@TableGenerator(name = "shopPictureGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "shopPicture", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "shopPicture")
public class ShopPic extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "shopPictureGenerator")
	private Long id;

	@Column(name = "SHOP_ID", nullable = false)
	private Long shopId;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "PIC_ID", nullable = false)
	private Pic pic;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}

}
