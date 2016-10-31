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

@Entity
@Table(name = "SHOP")
@TableGenerator(name = "shopGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "shop", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "shop")
public class Shop extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "shopGenerator")
	private Long id;

	@Column(name = "MANAGER_PROFILE_ID")
	private Long managerProfileId;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "PUBLIC_ACC_URL")
	private String publicAccURL;

	@Column(name = "ADDRESS", nullable = false)
	private String address;

	/**
	 * 经度
	 */
	@Column(name = "LONGITUDE")
	private Double longitude;

	/**
	 * 纬度
	 */
	@Column(name = "LATITUDE")
	private Double latitude;

	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
	private String description;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "PIC_ID", nullable = true)
	private Pic pic;
	
	@Column(name = "MOBILES", columnDefinition = "TEXT")
	private String mobiles;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getManagerProfileId() {
		return managerProfileId;
	}

	public void setManagerProfileId(Long managerProfileId) {
		this.managerProfileId = managerProfileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublicAccURL() {
		return publicAccURL;
	}

	public void setPublicAccURL(String publicAccURL) {
		this.publicAccURL = publicAccURL;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Pic getPic() {
		return pic;
	}

	public void setPic(Pic pic) {
		this.pic = pic;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	
}
