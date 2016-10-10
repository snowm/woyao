package com.woyao.wx;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDATAXmlAdapter extends XmlAdapter<String, String> {
	// 从javabean到xml的适配方法
	@Override
	public String marshal(String str) throws Exception {
		return "<![CDATA[" + str + "]]>";
	}

	// 从xml到javabean的适配方法
	@Override
	public String unmarshal(String str) throws Exception {
		return str;
	}
}
