package com.woyao;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
public class TestXMLObj {

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
