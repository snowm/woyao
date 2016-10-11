package com.woyao.domain.wx;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Table(name = "GLOBAL_ACCESS_TOKEN")
@TableGenerator(name = "globalAccessTokenGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "globalAccessToken", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "globalAccessToken")
public class GlobalAccessToken extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "globalAccessTokenGenerator")
	private Long id;

	@Column(name = "TOKEN", nullable = false, length = 1000)
	private String accessToken;

	@Column(name = "EXPIRES_IN", nullable = false)
	private Long expiresIn;

	@Column(name = "EFFECTIVE")
	private boolean effective = true;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public boolean isEffective() {
		return effective;
	}

	public void setEffective(boolean effective) {
		this.effective = effective;
	}

	public boolean isExpired() {
		long remainExpiringTime = this.getRemainExpiringTime();
		return remainExpiringTime <= 120;
	}

	public long getRemainExpiringTime() {
		long expiresIn = this.getExpiresIn();
		Date lastModifedDate = this.getModification().getLastModifiedDate();
		long durationTime = (System.currentTimeMillis() - lastModifedDate.getTime()) / 1000L;
		return expiresIn - durationTime;
	}
}
