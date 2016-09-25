package com.snowm.cat.jersey;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.cxf.common.logging.Log4jLogger;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class JerseyClientFactory implements FactoryBean<Client>, InitializingBean, DisposableBean {

    private Client client;

    private HttpClientConnectionManager connManager;
    private int connectTimeout;
    private int socketTimeout;
    private int connectionRequestTimeout;
    private Long maxIdleTime;
    private TimeUnit maxIdleTimeUnit;
    private RequestEntityProcessing requestEntityProcessing = RequestEntityProcessing.BUFFERED;

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(this.connectTimeout)
                .setConnectionRequestTimeout(this.connectionRequestTimeout).setSocketTimeout(this.socketTimeout)
                .build();

        ClientConfig clientConfig = new ClientConfig().property(ApacheClientProperties.CONNECTION_MANAGER, connManager)
                .property(ApacheClientProperties.REQUEST_CONFIG, requestConfig)
                .property(ActiveClientProperties.MAX_IDLE_TIME, this.maxIdleTime)
                .property(ActiveClientProperties.MAX_IDLE_TIME_UNIT, this.maxIdleTimeUnit)
                .property(ClientProperties.REQUEST_ENTITY_PROCESSING, this.requestEntityProcessing);
        ActiveApacheConnectorProvider connectorProvider = new ActiveApacheConnectorProvider();
        clientConfig.connectorProvider(connectorProvider);

        Logger logger = new Log4jLogger("JerseyClientLogging", null);
		LoggingFeature loggineFeature = new LoggingFeature(logger);
        ClientBuilder cb = ClientBuilder.newBuilder().withConfig(clientConfig).register(JacksonFeature.class)
        		.register(loggineFeature);
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
        if (this.connManager != null) {
            this.connManager.shutdown();
        }
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setConnManager(HttpClientConnectionManager connManager) {
        this.connManager = connManager;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public void setMaxIdleTime(Long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public void setMaxIdleTimeUnit(TimeUnit maxIdleTimeUnit) {
        this.maxIdleTimeUnit = maxIdleTimeUnit;
    }

    public void setRequestEntityProcessing(RequestEntityProcessing requestEntityProcessing) {
        this.requestEntityProcessing = requestEntityProcessing;
    }

}
