package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class ShopDTO extends BasePKDTO {

	private static final long serialVersionUID = 1L;

	private Long managerProfileId;

	private String managerName;
	
	private String managerPwd;
	
	private int managerType;

	private String name;

	private String address;

	/**
	 * 经度
	 */
	private Double longitude;

	/**
	 * 纬度
	 */
	private Double latitude;

	private String description;

	private String publicAccURL;

	private Long picId;

	private String picUrl;

	public Long getManagerProfileId() {
		return managerProfileId;
	}

	public void setManagerProfileId(Long managerProfileId) {
		this.managerProfileId = managerProfileId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPublicAccURL() {
		return publicAccURL;
	}

	public void setPublicAccURL(String publicAccURL) {
		this.publicAccURL = publicAccURL;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getManagerPwd() {
		return managerPwd;
	}

	public void setManagerPwd(String managerPwd) {
		this.managerPwd = managerPwd;
	}

	public int getManagerType() {
		return managerType;
	}

	public void setManagerType(int managerType) {
		this.managerType = managerType;
	}


}
