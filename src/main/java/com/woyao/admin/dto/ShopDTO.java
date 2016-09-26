package com.woyao.admin.dto;

public class ShopDTO extends BasePKDTO {

	private static final long serialVersionUID = 2260608506954249912L;

	private Long id;
	private String name;

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

}
