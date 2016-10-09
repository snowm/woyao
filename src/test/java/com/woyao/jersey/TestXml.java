package com.woyao.jersey;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.cxf.common.logging.Log4jLogger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.netty.connector.NettyConnectorProvider;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestXml {

	private Client client;

	@Before
	public void init() {
		this.client = this.initClient();
	}

	private Client initClient() {
		ClientConfig clientConfig = new ClientConfig().property(ClientProperties.ASYNC_THREADPOOL_SIZE, 2)
				.property(ClientProperties.READ_TIMEOUT, 10000).property(ClientProperties.CONNECT_TIMEOUT, 3000);
		NettyConnectorProvider connectorProvider = new NettyConnectorProvider();
		clientConfig.connectorProvider(connectorProvider);

		Logger logger = new Log4jLogger("JerseyClientLogging", null);
		LoggingFeature loggineFeature = new LoggingFeature(logger);
		ClientBuilder cb = ClientBuilder.newBuilder().withConfig(clientConfig).register(JacksonFeature.class).register(loggineFeature);
		return cb.build();
	}
	
	@Test
	public void testPostXML(){
		String uri = null;
		this.client.target(uri);
	}
}
