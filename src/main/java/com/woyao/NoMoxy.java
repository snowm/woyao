package com.woyao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class NoMoxy {

	@XmlCDATA
	@XmlElement(name = Elements.ID, required = false)
	private String id;

	@XmlCDATA
	@XmlElement(name = Elements.NAME, required = false)
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

	static final class Elements {
		static final String ID = "Id";
		static final String NAME = "Name";

		private Elements() {
		}
	}

}
