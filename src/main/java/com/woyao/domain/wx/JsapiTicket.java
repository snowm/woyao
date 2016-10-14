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
@Table(name = "JSAPI_TICKET")
@TableGenerator(name = "jsapiTicketGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "jsapiTicket", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "jsapiTicket")
public class JsapiTicket extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "jsapiTicketGenerator")
	private Long id;

	@Column(name = "TICKET", nullable = false, length = 1000)
	private String ticket;

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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
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
