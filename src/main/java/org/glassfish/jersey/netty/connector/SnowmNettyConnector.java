package org.glassfish.jersey.netty.connector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.spi.AsyncConnectorCallback;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.netty.connector.internal.JerseyChunkedInput;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.proxy.HttpProxyHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.JdkSslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GenericFutureListener;
import jersey.repackaged.com.google.common.util.concurrent.SettableFuture;

/**
 * Netty connector implementation.
 *
 * @author jade.young
 */
class SnowmNettyConnector extends NettyConnector {

	SnowmNettyConnector(Client client) {
		super(client);
	}

	@Override
	public ClientResponse apply(ClientRequest jerseyRequest) {

		final AtomicReference<ClientResponse> syncResponse = new AtomicReference<>(null);
		final AtomicReference<Throwable> syncException = new AtomicReference<>(null);

		try {
			Future<?> resultFuture = apply(jerseyRequest, new AsyncConnectorCallback() {
				@Override
				public void response(ClientResponse response) {
					syncResponse.set(response);
				}

				@Override
				public void failure(Throwable failure) {
					syncException.set(failure);
				}
			});

			Integer timeout = ClientProperties.getValue(jerseyRequest.getConfiguration().getProperties(), ClientProperties.READ_TIMEOUT, 0);

			if (timeout != null && timeout > 0) {
				resultFuture.get(timeout, TimeUnit.MILLISECONDS);
			} else {
				resultFuture.get();
			}
		} catch (ExecutionException ex) {
			Throwable e = ex.getCause() == null ? ex : ex.getCause();
			throw new ProcessingException(e.getMessage(), e);
		} catch (Exception ex) {
			throw new ProcessingException(ex.getMessage(), ex);
		}

		Throwable throwable = syncException.get();
		if (throwable == null) {
			return syncResponse.get();
		} else {
			throw new RuntimeException(throwable);
		}
	}

	@Override
	public Future<?> apply(final ClientRequest jerseyRequest, final AsyncConnectorCallback jerseyCallback) {

		final SettableFuture<Object> settableFuture = SettableFuture.create();

		final URI requestUri = jerseyRequest.getUri();
		String host = requestUri.getHost();
		int port = requestUri.getPort() != -1 ? requestUri.getPort() : "https".equals(requestUri.getScheme()) ? 443 : 80;

		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();

					// Enable HTTPS if necessary.
					if ("https".equals(requestUri.getScheme())) {
						// making client authentication optional for now; it
						// could be extracted to configurable property
						JdkSslContext jdkSslContext = new JdkSslContext(client.getSslContext(), true, ClientAuth.NONE);
						p.addLast(jdkSslContext.newHandler(ch.alloc()));
					}

					// http proxy
					Configuration config = jerseyRequest.getConfiguration();
					final Object proxyUri = config.getProperties().get(ClientProperties.PROXY_URI);
					if (proxyUri != null) {
						final URI u = getProxyUri(proxyUri);

						final String userName = ClientProperties.getValue(config.getProperties(), ClientProperties.PROXY_USERNAME,
								String.class);
						final String password = ClientProperties.getValue(config.getProperties(), ClientProperties.PROXY_PASSWORD,
								String.class);

						p.addLast(new HttpProxyHandler(new InetSocketAddress(u.getHost(), u.getPort() == -1 ? 8080 : u.getPort()), userName,
								password));
					}

					p.addLast(new HttpClientCodec());
					p.addLast(new ChunkedWriteHandler());
					p.addLast(new HttpContentDecompressor());
					p.addLast(new JerseyClientHandler(SnowmNettyConnector.this, jerseyRequest, jerseyCallback, settableFuture));
				}
			});

			// connect timeout
			Integer connectTimeout = ClientProperties.getValue(jerseyRequest.getConfiguration().getProperties(),
					ClientProperties.CONNECT_TIMEOUT, 0);
			if (connectTimeout > 0) {
				b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout);
			}

			// Make the connection attempt.
			final Channel ch = b.connect(host, port).sync().channel();

			// guard against prematurely closed channel
			final GenericFutureListener<io.netty.util.concurrent.Future<? super Void>> closeListener = new GenericFutureListener<io.netty.util.concurrent.Future<? super Void>>() {
				@Override
				public void operationComplete(io.netty.util.concurrent.Future<? super Void> future) throws Exception {
					if (!settableFuture.isDone()) {
						settableFuture.setException(new IOException("Channel closed."));
					}
				}
			};

			ch.closeFuture().addListener(closeListener);

			HttpRequest nettyRequest;

			String url = requestUri.getRawPath();
			if (!StringUtils.isBlank(requestUri.getRawQuery())) {
				url = url + "?" + requestUri.getRawQuery();
			}
			if (jerseyRequest.hasEntity()) {
				nettyRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(jerseyRequest.getMethod()), url);
			} else {
				nettyRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.valueOf(jerseyRequest.getMethod()), url);
			}

			// headers
			for (final Map.Entry<String, List<String>> e : jerseyRequest.getStringHeaders().entrySet()) {
				nettyRequest.headers().add(e.getKey(), e.getValue());
			}

			// host header - http 1.1
			nettyRequest.headers().add(HttpHeaderNames.HOST, jerseyRequest.getUri().getHost());

			if (jerseyRequest.hasEntity()) {
				if (jerseyRequest.getLength() == -1) {
					HttpUtil.setTransferEncodingChunked(nettyRequest, true);
				} else {
					nettyRequest.headers().add(HttpHeaderNames.CONTENT_LENGTH, jerseyRequest.getLength());
				}
			}

			if (jerseyRequest.hasEntity()) {
				// Send the HTTP request.
				ch.writeAndFlush(nettyRequest);

				final JerseyChunkedInput jerseyChunkedInput = new JerseyChunkedInput(ch);
				jerseyRequest.setStreamProvider(new OutboundMessageContext.StreamProvider() {
					@Override
					public OutputStream getOutputStream(int contentLength) throws IOException {
						return jerseyChunkedInput;
					}
				});

				if (HttpUtil.isTransferEncodingChunked(nettyRequest)) {
					ch.write(new HttpChunkedInput(jerseyChunkedInput));
				} else {
					ch.write(jerseyChunkedInput);
				}

				executorService.execute(new Runnable() {
					@Override
					public void run() {
						// close listener is not needed any more.
						ch.closeFuture().removeListener(closeListener);

						try {
							jerseyRequest.writeEntity();
						} catch (IOException e) {
							jerseyCallback.failure(e);
							settableFuture.setException(e);
						}
					}
				});

				ch.flush();
			} else {
				// close listener is not needed any more.
				ch.closeFuture().removeListener(closeListener);

				// Send the HTTP request.
				ch.writeAndFlush(nettyRequest);
			}

		} catch (InterruptedException e) {
			settableFuture.setException(e);
			return settableFuture;
		}

		return settableFuture;
	}

	@Override
	public String getName() {
		return "Netty 4.1.x";
	}

	@Override
	public void close() {
		group.shutdownGracefully();
		executorService.shutdown();
	}

	@SuppressWarnings("ChainOfInstanceofChecks")
	private static URI getProxyUri(final Object proxy) {
		if (proxy instanceof URI) {
			return (URI) proxy;
		} else if (proxy instanceof String) {
			return URI.create((String) proxy);
		} else {
			throw new ProcessingException(LocalizationMessages.WRONG_PROXY_URI_TYPE(ClientProperties.PROXY_URI));
		}
	}
}
