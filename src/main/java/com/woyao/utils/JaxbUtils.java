package com.woyao.utils;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbUtils {

	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Class<T> clazz, String content) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		T object = (T) unmarshaller.unmarshal(new StringReader(content));
		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(Class<T> clazz, File file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		T object = (T) unmarshaller.unmarshal(file);
		return object;
	}

	public static String marshall(Object obj, String encodeingCharset) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, encodeingCharset);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		StringWriter sw = new StringWriter();
		marshaller.marshal(obj, sw);
		String result = sw.toString();
		return result;
	}

	public static String marshall(Object obj) throws JAXBException {
		String result = marshall(obj, "UTF-8");
		return result;
	}
}
