package com.woyao.wx.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "xml")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "OrderResponse")
public class OrderReportResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "return_code", required = true)
	private String returnCode;

	@XmlElement(name = "return_msg", required = true)
	private String returnMsg;

}
