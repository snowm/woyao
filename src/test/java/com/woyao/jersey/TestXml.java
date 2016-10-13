package com.woyao.jersey;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.spi.ConnectorProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.netty.connector.SnowmNettyConnectorProvider;

import com.woyao.utils.JaxbUtils;
import com.woyao.wx.dto.TestXMLObj;

public class TestXml {

	private Client client;
	private Client apacheClient;

	public TestXml() {
		this.client = this.initClient();
		this.apacheClient = this.initApacheClient();
	}

	private Client initClient() {
		ClientConfig clientConfig = new ClientConfig().property(ClientProperties.ASYNC_THREADPOOL_SIZE, 2)
				.property(ClientProperties.READ_TIMEOUT, 10000).property(ClientProperties.CONNECT_TIMEOUT, 3000);
		ConnectorProvider connectorProvider = new SnowmNettyConnectorProvider();
		clientConfig.connectorProvider(connectorProvider);

		Logger logger = new Log4jLogger("JerseyClientLogging", null);
		LoggingFeature loggingFeature = new LoggingFeature(logger);
		ClientBuilder cb = ClientBuilder.newBuilder().withConfig(clientConfig).register(JacksonFeature.class).register(loggingFeature);
		return cb.build();
	}

	private Client initApacheClient() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(10000).build();

		HttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		ClientConfig clientConfig = new ClientConfig().property(ApacheClientProperties.CONNECTION_MANAGER, connManager)
				.property(ApacheClientProperties.REQUEST_CONFIG, requestConfig);
		SnowmApacheConnectorProvider connectorProvider = new SnowmApacheConnectorProvider();
		clientConfig.connectorProvider(connectorProvider);

		Logger logger = new Log4jLogger("JerseyClientLogging", null);
		LoggingFeature loggineFeature = new LoggingFeature(logger);
		ClientBuilder cb = ClientBuilder.newBuilder().withConfig(clientConfig).register(JacksonFeature.class).register(loggineFeature);
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
	
	private void testGetUserAccessTokenNetty() {
		String code = "031YQUDq0Yychb1LBpFq0wSTDq0YQUDo";
		String uri = "https://api.weixin.qq.com/sns/oauth2/access_token";
		WebTarget target = this.client.target(uri).queryParam("appid", "wxf55a7c00ffaca994")
				.queryParam("secret", "c2a3b331343402b2ef8ad60851c80e73").queryParam("code", code)
				.queryParam("grant_type", "authorization_code");
		Response resp = target.request().get();
	}

	private void testGetUserAccessTokenApache() {
		String code = "031YQUDq0Yychb1LBpFq0wSTDq0YQUDo";
		String uri = "https://api.weixin.qq.com/sns/oauth2/access_token";
		WebTarget target = this.apacheClient.target(uri).queryParam("appid", "wxf55a7c00ffaca994")
				.queryParam("secret", "c2a3b331343402b2ef8ad60851c80e73").queryParam("code", code)
				.queryParam("grant_type", "authorization_code");
		Response resp = target.request().get();
	}

	private void testNettyGet() {
		String code = "031YQUDq0Yychb1LBpFq0wSTDq0YQUDo";
		String uri = "http://luoke30.com/sns/oauth2/access_token";
		WebTarget target = this.client.target(uri).queryParam("appid", "wxf55a7c00ffaca994")
				.queryParam("secret", "c2a3b331343402b2ef8ad60851c80e73").queryParam("code", code)
				.queryParam("grant_type", "authorization_code");
		Response resp = target.request().get();
	}
	private void testApacheGet() {
		String code = "031YQUDq0Yychb1LBpFq0wSTDq0YQUDo";
		String uri = "http://luoke30.com/sns/oauth2/access_token";
		WebTarget target = this.apacheClient.target(uri).queryParam("appid", "wxf55a7c00ffaca994")
				.queryParam("secret", "c2a3b331343402b2ef8ad60851c80e73").queryParam("code", code)
				.queryParam("grant_type", "authorization_code");
		Response resp = target.request().get();
	}
	
	public static void main(String[] args) {
		try{
		URI.create("https://api.weixin.qq.com/sns/userinfo");
		}catch(Exception ex){
			ex.printStackTrace();
		}
//		TestXml t = new TestXml();
//		t.testGetUserAccessTokenNetty();
		// try {
		// t.testPostXML();
		// } catch (JAXBException e) {
		// e.printStackTrace();
		// }

	}

}
