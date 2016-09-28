package com.woyao.customer.dto;

import java.io.Serializable;

public class ShopDTO implements Serializable {

	private static final long serialVersionUID = 2260608506954249912L;

	private Long id;

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

	private String picURL;

	private Long chatRoomId;

	private String chatRoomName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	public Long getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getChatRoomName() {
		return chatRoomName;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

}
