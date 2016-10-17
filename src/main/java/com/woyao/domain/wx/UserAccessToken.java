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
@Table(name = "USER_ACCESS_TOKEN")
@TableGenerator(name = "userAccessTokenGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "userAccessToken", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userAccessToken")
public class UserAccessToken extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "userAccessTokenGenerator")
	private Long id;

	@Column(name = "PROFILE_WX_ID", nullable = true, unique = true)
	private Long profileWXId;

	@Column(name = "TOKEN", nullable = false, length = 1000)
	private String accessToken;

	@Column(name = "EXPIRES_IN", nullable = false)
	private Long expiresIn;

	@Column(name = "REFRESH_TOKEN", nullable = false)
	private String refreshToken;

	@Column(name = "OPEN_ID", nullable = false, unique = true)
	private String openId;

	@Column(name = "SCOPE", nullable = true)
	private String scope;

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

	public Long getProfileWXId() {
		return profileWXId;
	}

	public void setProfileWXId(Long profileWXId) {
		this.profileWXId = profileWXId;
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

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
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
