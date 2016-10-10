package com.woyao.utils;

import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.woyao.wx.dto.TestXMLObj;

public class TestJaxbUtils {

	private String content = "<xml><Id><![CDATA[id1]]></Id><Name><![CDATA[name1]]></Name></xml>";

	@Test
	public void testUnmarshall() {
		try {
			TestXMLObj obj = JaxbUtils.unmarshall(TestXMLObj.class, this.content);
			assertEquals("id1", obj.getId());
			assertEquals("name1", obj.getName());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testMarshall() {
		try {
			TestXMLObj obj = new TestXMLObj();
			obj.setId("id11");
			obj.setName("name11");
			String marshalled = JaxbUtils.marshall(obj);
			System.out.println(marshalled);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
