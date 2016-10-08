package com.woyao.domain.wx;

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

	@Column(name = "TOKEN", nullable = false, length = 1000)
	private String accessToken;

	@Column(name = "EXPIRES_IN", nullable = false)
	private Long expiresIn;

	@Column(name = "REFRESH_TOKEN", nullable = false)
	private String refreshToken;

	@Column(name = "OPEN_ID", nullable = false)
	private String openid;

	@Column(name = "SCOPE", nullable = false)
	private String scope;

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

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
