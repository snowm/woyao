package com.woyao.domain.profile;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.snowm.security.profile.domain.Gender;

@Entity
@Table(name = "PROFILE_WX")
@TableGenerator(name = "profileWXGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "profileWX", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "profileWX")
public class ProfileWX extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "profileWXGenerator")
	private Long id;

	@Column(name = "OPENID", length = 50, nullable = false, unique = true)
	private String openId;

	@Column(name = "NICKNAME", length = 100)
	private String nickname;

	@Column(name = "GENDER", nullable = false, length = 2)
	@Type(type = "com.snowm.hibernate.ext.usertype.ExtEnumType", parameters = {
			@Parameter(name = "enumClass", value = "com.snowm.security.profile.domain.Gender") })
	private Gender gender;

	@Column(name = "PROVINCE", length = 50)
	private String province;

	@Column(name = "CITY", length = 50)
	private String city;

	@Column(name = "COUNTRY", length = 50)
	private String country;

	@Column(name = "HEAD_IMG", length = 255)
	private String headImg;

	// 用户特权信息，JSON数组
	@Column(name = "PRIVILEGE", length = 255)
	private String privilege;

	@Column(name = "UNIONID", length = 50)
	private String unionId;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

}
