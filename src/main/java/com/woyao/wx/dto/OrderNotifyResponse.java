package com.woyao.wx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderNotifyResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlCDATA
	@XmlElement(name = "return_code", required = true)
	private String returnCode;

	@XmlCDATA
	@XmlElement(name = "return_msg")
	private String returnMsg;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
