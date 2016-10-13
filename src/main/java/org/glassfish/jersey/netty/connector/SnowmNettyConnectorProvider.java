package org.glassfish.jersey.netty.connector;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;

import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.client.spi.ConnectorProvider;

public class SnowmNettyConnectorProvider implements ConnectorProvider {

	@Override
	public Connector getConnector(Client client, Configuration runtimeConfig) {
		return new SnowmNettyConnector(client);
	}
}