package com.woyao.jersey;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.cxf.common.logging.Log4jLogger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.netty.connector.NettyConnectorProvider;

import com.woyao.utils.JaxbUtils;
import com.woyao.wx.dto.TestXMLObj;

public class TestXml {

	private Client client;

	public TestXml() {
		this.client = this.initClient();
	}

	private Client initClient() {
		ClientConfig clientConfig = new ClientConfig().property(ClientProperties.ASYNC_THREADPOOL_SIZE, 2)
				.property(ClientProperties.READ_TIMEOUT, 10000).property(ClientProperties.CONNECT_TIMEOUT, 3000);
		NettyConnectorProvider connectorProvider = new NettyConnectorProvider();
		clientConfig.connectorProvider(connectorProvider);

		Logger logger = new Log4jLogger("JerseyClientLogging", null);
		LoggingFeature loggingFeature = new LoggingFeature(logger);
		ClientBuilder cb = ClientBuilder.newBuilder().withConfig(clientConfig).register(JacksonFeature.class).register(loggingFeature);
		return cb.build();
	}

	private void testPostXML() throws JAXBException {
		String uri = "http://localhost:8080/wx/orderNotify";
		WebTarget target = this.client.target(uri);
		String content = "<xml><Id><![CDATA[id2]]></Id><Name><![CDATA[name1]]></Name></xml>";
		Entity<String> entity = Entity.entity(content, MediaType.TEXT_PLAIN_TYPE);
		Response resp = target.request().post(entity);
		String respBody = resp.readEntity(String.class);
		TestXMLObj respObj = JaxbUtils.unmarshall(TestXMLObj.class, respBody);
		assertEquals("id2-1", respObj.getId());
	}

	public static void main(String[] args) {
		TestXml t = new TestXml();
		try {
			t.testPostXML();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
