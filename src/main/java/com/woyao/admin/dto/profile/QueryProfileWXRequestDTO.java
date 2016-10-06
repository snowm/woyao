package com.woyao.admin.dto.profile;

import com.woyao.PaginationQueryRequestDTO;

public class QueryProfileWXRequestDTO extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 6065375426171420402L;

	private String nickname;
	
	private Integer genderId;
	
	private String city;
	
	private String country;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGenderId() {
		return genderId;
	}

	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
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
	
	
}
