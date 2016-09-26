package com.woyao.domain.profile;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.snowm.security.profile.domain.Gender;
import com.snowm.security.profile.domain.Profile;

@Entity
@PrimaryKeyJoinColumn(name = "ID")
@Table(name = "PROFILE_WX")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "profileWX")
public class ProfileWX extends Profile {

	private static final long serialVersionUID = 1L;

	private String openId;

	private String nickname;

	private Gender gender;

	private String province;

	private String city;
	
	private String country;
	
	private String headImg;
	// 用户特权信息，JSON数组
//	@
	private String privilege;
	
	private String unionId;

}
