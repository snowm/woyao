package com.woyao.jersey;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.spi.ConnectorProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.netty.connector.SnowmNettyConnectorProvider;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class JerseyNettyClientFactory implements FactoryBean<Client>, InitializingBean, DisposableBean {

	private Client client;

	private int asyncThreadPoolSize;
	private int connectTimeout;
	private int socketTimeout;

	@Override
	public void afterPropertiesSet() throws Exception {
		ClientConfig clientConfig = new ClientConfig().property(ClientProperties.ASYNC_THREADPOOL_SIZE, this.asyncThreadPoolSize)
				.property(ClientProperties.READ_TIMEOUT, this.socketTimeout)
				.property(ClientProperties.CONNECT_TIMEOUT, this.connectTimeout);
		ConnectorProvider connectorProvider = new SnowmNettyConnectorProvider();
		clientConfig.connectorProvider(connectorProvider);

//		Logger logger = new Log4jLogger("JerseyClientLogging", null);
//		LoggingFeature loggingFeature = new LoggingFeature(logger);
		ClientBuilder cb = ClientBuilder.newBuilder().withConfig(clientConfig).register(JacksonFeature.class).register(LoggingFeature.class);
		this.client = cb.build();
	}

	@Override
	public Client getObject() throws Exception {
		return this.client;
	}

	@Override
	public Class<?> getObjectType() {
		return this.client != null ? this.client.getClass() : Client.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		if (this.client != null) {
			this.client.close();
		}
	}

	public void setAsyncThreadPoolSize(int asyncThreadPoolSize) {
		this.asyncThreadPoolSize = asyncThreadPoolSize;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

}
