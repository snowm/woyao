package com.woyao.jersey;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Configuration;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.glassfish.jersey.apache.connector.LocalizationMessages;
import org.glassfish.jersey.client.Initializable;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.client.spi.ConnectorProvider;

public class SnowmApacheConnectorProvider implements ConnectorProvider {

    @Override
    public Connector getConnector(final Client client, final Configuration runtimeConfig) {
        return new SnowmApacheConnector(client, runtimeConfig);
    }

    public static HttpClient getHttpClient(final Configurable<?> component) {
        return getConnector(component).getHttpClient();
    }

    public static CookieStore getCookieStore(final Configurable<?> component) {
        return getConnector(component).getCookieStore();
    }

    private static SnowmApacheConnector getConnector(final Configurable<?> component) {
        if (!(component instanceof Initializable)) {
            throw new IllegalArgumentException(
                    LocalizationMessages.INVALID_CONFIGURABLE_COMPONENT_TYPE(component.getClass().getName()));
        }

        final Initializable<?> initializable = (Initializable<?>) component;
        Connector connector = initializable.getConfiguration().getConnector();
        if (connector == null) {
            initializable.preInitialize();
            connector = initializable.getConfiguration().getConnector();
        }

        if (connector instanceof SnowmApacheConnector) {
            return (SnowmApacheConnector) connector;
        } else {
            throw new IllegalArgumentException(LocalizationMessages.EXPECTED_CONNECTOR_PROVIDER_NOT_USED());
        }
    }
}
