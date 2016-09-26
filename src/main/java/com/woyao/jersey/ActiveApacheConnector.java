package com.woyao.jersey;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultManagedHttpClientConnection;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.ChunkedOutputStream;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.TextUtils;
import org.apache.http.util.VersionInfo;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.LocalizationMessages;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.client.spi.AsyncConnectorCallback;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.message.internal.HeaderUtils;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.message.internal.Statuses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jersey.repackaged.com.google.common.util.concurrent.MoreExecutors;

@SuppressWarnings("deprecation")
public class ActiveApacheConnector implements Connector {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveApacheConnector.class);

    private static final VersionInfo vi;
    private static final String release;

    static {
        vi = VersionInfo.loadVersionInfo("org.apache.http.client", HttpClientBuilder.class.getClassLoader());
        release = (vi != null) ? vi.getRelease() : VersionInfo.UNAVAILABLE;
    }

    private final CloseableHttpClient client;
    private final CookieStore cookieStore;
    private final boolean preemptiveBasicAuth;
    private final RequestConfig requestConfig;
    private final Long maxIdleTime;
    private final TimeUnit maxIdleTimeUnit;

    /**
     * Create the new Apache HTTP Client connector.
     *
     * @param client JAX-RS client instance for which the connector is being created.
     * @param config client configuration.
     */
    public ActiveApacheConnector(final Client client, final Configuration config) {
        final Object connectionManager = config.getProperties().get(ApacheClientProperties.CONNECTION_MANAGER);
        if (connectionManager != null) {
            if (!(connectionManager instanceof HttpClientConnectionManager)) {
                LOGGER.warn(
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ApacheClientProperties.CONNECTION_MANAGER,
                                connectionManager.getClass().getName(),
                                HttpClientConnectionManager.class.getName())
                );
            }
        }

        Object reqConfig = config.getProperties().get(ApacheClientProperties.REQUEST_CONFIG);
        if (reqConfig != null) {
            if (!(reqConfig instanceof RequestConfig)) {
                LOGGER.warn(
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ApacheClientProperties.REQUEST_CONFIG,
                                reqConfig.getClass().getName(),
                                RequestConfig.class.getName())
                );
                reqConfig = null;
            }
        }

        final SSLContext sslContext = client.getSslContext();
        final HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setConnectionManager(getConnectionManager(client, config, sslContext));
        clientBuilder.setConnectionManagerShared(
                PropertiesHelper.getValue(config.getProperties(), ApacheClientProperties.CONNECTION_MANAGER_SHARED, false, null));
        clientBuilder.setSslcontext(sslContext);
        
        clientBuilder.evictExpiredConnections();
        Object propMaxIdleTime = config.getProperty(ActiveClientProperties.MAX_IDLE_TIME);
        if (propMaxIdleTime != null) {
            if (!(propMaxIdleTime instanceof Long)) {
                LOGGER.warn(
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ActiveClientProperties.MAX_IDLE_TIME,
                                propMaxIdleTime.getClass().getName(),
                                Long.class.getName())
                );
                propMaxIdleTime = null;
            }
        }
        Object propMaxIdleTimeUnit = config.getProperty(ActiveClientProperties.MAX_IDLE_TIME_UNIT);
        if (propMaxIdleTimeUnit != null) {
            if (!(propMaxIdleTimeUnit instanceof TimeUnit)) {
                LOGGER.warn(
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ActiveClientProperties.MAX_IDLE_TIME_UNIT,
                                propMaxIdleTimeUnit.getClass().getName(),
                                TimeUnit.class.getName())
                );
                propMaxIdleTimeUnit = null;
            }
        }
        if (propMaxIdleTime != null && propMaxIdleTimeUnit != null) {
            this.maxIdleTime = (Long) propMaxIdleTime;
            this.maxIdleTimeUnit = (TimeUnit) propMaxIdleTimeUnit;
            clientBuilder.evictIdleConnections(this.maxIdleTime, this.maxIdleTimeUnit);
        } else {
            this.maxIdleTime = null;
            this.maxIdleTimeUnit = null;
        }

        final RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

        final Object credentialsProvider = config.getProperty(ApacheClientProperties.CREDENTIALS_PROVIDER);
        if (credentialsProvider != null && (credentialsProvider instanceof CredentialsProvider)) {
            clientBuilder.setDefaultCredentialsProvider((CredentialsProvider) credentialsProvider);
        }

        final Object proxyUri;
        proxyUri = config.getProperty(ClientProperties.PROXY_URI);
        if (proxyUri != null) {
            final URI u = getProxyUri(proxyUri);
            final HttpHost proxy = new HttpHost(u.getHost(), u.getPort(), u.getScheme());
            final String userName;
            userName = ClientProperties.getValue(config.getProperties(), ClientProperties.PROXY_USERNAME, String.class);
            if (userName != null) {
                final String password;
                password = ClientProperties.getValue(config.getProperties(), ClientProperties.PROXY_PASSWORD, String.class);

                if (password != null) {
                    final CredentialsProvider credsProvider = new BasicCredentialsProvider();
                    credsProvider.setCredentials(
                            new AuthScope(u.getHost(), u.getPort()),
                            new UsernamePasswordCredentials(userName, password)
                    );
                    clientBuilder.setDefaultCredentialsProvider(credsProvider);
                }
            }
            clientBuilder.setProxy(proxy);
        }

        final Boolean preemptiveBasicAuthProperty = (Boolean) config.getProperties()
                .get(ApacheClientProperties.PREEMPTIVE_BASIC_AUTHENTICATION);
        this.preemptiveBasicAuth = (preemptiveBasicAuthProperty != null) ? preemptiveBasicAuthProperty : false;

        final boolean ignoreCookies = PropertiesHelper.isProperty(config.getProperties(), ApacheClientProperties.DISABLE_COOKIES);

        if (reqConfig != null) {
            final RequestConfig.Builder reqConfigBuilder = RequestConfig.copy((RequestConfig) reqConfig);
            if (ignoreCookies) {
                reqConfigBuilder.setCookieSpec(CookieSpecs.IGNORE_COOKIES);
            }
            requestConfig = reqConfigBuilder.build();
        } else {
            if (ignoreCookies) {
                requestConfigBuilder.setCookieSpec(CookieSpecs.IGNORE_COOKIES);
            }
            requestConfig = requestConfigBuilder.build();
        }

        if (requestConfig.getCookieSpec() == null || !requestConfig.getCookieSpec().equals(CookieSpecs.IGNORE_COOKIES)) {
            this.cookieStore = new BasicCookieStore();
            clientBuilder.setDefaultCookieStore(cookieStore);
        } else {
            this.cookieStore = null;
        }
        clientBuilder.setDefaultRequestConfig(requestConfig);
        this.client = clientBuilder.build();
    }

    private HttpClientConnectionManager getConnectionManager(final Client client,
                                                             final Configuration config,
                                                             final SSLContext sslContext) {
        final Object cmObject = config.getProperties().get(ApacheClientProperties.CONNECTION_MANAGER);

        // Connection manager from configuration.
        if (cmObject != null) {
            if (cmObject instanceof HttpClientConnectionManager) {
                return (HttpClientConnectionManager) cmObject;
            } else {
                LOGGER.warn(
                        LocalizationMessages.IGNORING_VALUE_OF_PROPERTY(
                                ApacheClientProperties.CONNECTION_MANAGER,
                                cmObject.getClass().getName(),
                                HttpClientConnectionManager.class.getName())
                );
            }
        }

        // Create custom connection manager.
        return createConnectionManager(
                client,
                config,
                sslContext,
                false);
    }

    private HttpClientConnectionManager createConnectionManager(
            final Client client,
            final Configuration config,
            final SSLContext sslContext,
            final boolean useSystemProperties) {

        final String[] supportedProtocols = useSystemProperties ? split(
                System.getProperty("https.protocols")) : null;
        final String[] supportedCipherSuites = useSystemProperties ? split(
                System.getProperty("https.cipherSuites")) : null;

        HostnameVerifier hostnameVerifier = client.getHostnameVerifier();

        final LayeredConnectionSocketFactory sslSocketFactory;
        if (sslContext != null) {
            sslSocketFactory = new SSLConnectionSocketFactory(
                    sslContext, supportedProtocols, supportedCipherSuites, hostnameVerifier);
        } else {
            if (useSystemProperties) {
                sslSocketFactory = new SSLConnectionSocketFactory(
                        (SSLSocketFactory) SSLSocketFactory.getDefault(),
                        supportedProtocols, supportedCipherSuites, hostnameVerifier);
            } else {
                sslSocketFactory = new SSLConnectionSocketFactory(
                        SSLContexts.createDefault(),
                        hostnameVerifier);
            }
        }

        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory)
                .build();

        final Integer chunkSize = ClientProperties.getValue(config.getProperties(),
                ClientProperties.CHUNKED_ENCODING_SIZE, ClientProperties.DEFAULT_CHUNK_SIZE, Integer.class);

        final PoolingHttpClientConnectionManager connectionManager =
                new PoolingHttpClientConnectionManager(registry, new ConnectionFactory(chunkSize));

        if (useSystemProperties) {
            String s = System.getProperty("http.keepAlive", "true");
            if ("true".equalsIgnoreCase(s)) {
                s = System.getProperty("http.maxConnections", "5");
                final int max = Integer.parseInt(s);
                connectionManager.setDefaultMaxPerRoute(max);
                connectionManager.setMaxTotal(2 * max);
            }
        }

        return connectionManager;
    }

    private static String[] split(final String s) {
        if (TextUtils.isBlank(s)) {
            return null;
        }
        return s.split(" *, *");
    }

    /**
     * Get the {@link HttpClient}.
     *
     * @return the {@link HttpClient}.
     */
    public HttpClient getHttpClient() {
        return client;
    }

    /**
     * Get the {@link CookieStore}.
     *
     * @return the {@link CookieStore} instance or {@code null} when {@value ApacheClientProperties#DISABLE_COOKIES} set to
     * {@code true}.
     */
    public CookieStore getCookieStore() {
        return cookieStore;
    }

    private static URI getProxyUri(final Object proxy) {
        if (proxy instanceof URI) {
            return (URI) proxy;
        } else if (proxy instanceof String) {
            return URI.create((String) proxy);
        } else {
            throw new ProcessingException(LocalizationMessages.WRONG_PROXY_URI_TYPE(ClientProperties.PROXY_URI));
        }
    }

    @Override
    public ClientResponse apply(final ClientRequest clientRequest) throws ProcessingException {
        final HttpUriRequest request = getUriHttpRequest(clientRequest);
        final Map<String, String> clientHeadersSnapshot = writeOutBoundHeaders(clientRequest.getHeaders(), request);

        try {
            final CloseableHttpResponse response;
            final HttpClientContext context = HttpClientContext.create();
            if (preemptiveBasicAuth) {
                final AuthCache authCache = new BasicAuthCache();
                final BasicScheme basicScheme = new BasicScheme();
                authCache.put(getHost(request), basicScheme);
                context.setAuthCache(authCache);
            }
            response = client.execute(getHost(request), request, context);
            HeaderUtils.checkHeaderChanges(clientHeadersSnapshot, clientRequest.getHeaders(), this.getClass().getName());

            final Response.StatusType status = response.getStatusLine().getReasonPhrase() == null
                    ? Statuses.from(response.getStatusLine().getStatusCode())
                    : Statuses.from(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());

            final ClientResponse responseContext = new ClientResponse(status, clientRequest);
            final List<URI> redirectLocations = context.getRedirectLocations();
            if (redirectLocations != null && !redirectLocations.isEmpty()) {
                responseContext.setResolvedRequestUri(redirectLocations.get(redirectLocations.size() - 1));
            }

            final Header[] respHeaders = response.getAllHeaders();
            final MultivaluedMap<String, String> headers = responseContext.getHeaders();
            for (final Header header : respHeaders) {
                final String headerName = header.getName();
                List<String> list = headers.get(headerName);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(header.getValue());
                headers.put(headerName, list);
            }

            final HttpEntity entity = response.getEntity();

            if (entity != null) {
                if (headers.get(HttpHeaders.CONTENT_LENGTH) == null) {
                    headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(entity.getContentLength()));
                }

                final Header contentEncoding = entity.getContentEncoding();
                if (headers.get(HttpHeaders.CONTENT_ENCODING) == null && contentEncoding != null) {
                    headers.add(HttpHeaders.CONTENT_ENCODING, contentEncoding.getValue());
                }
            }

            try {
                responseContext.setEntityStream(new HttpClientResponseInputStream(getInputStream(response)));
            } catch (final IOException e) {
                LOGGER.error(null, e);
            }

            return responseContext;
        } catch (final Exception e) {
            throw new ProcessingException(e);
        }
    }

    @Override
    public Future<?> apply(final ClientRequest request, final AsyncConnectorCallback callback) {
        return MoreExecutors.sameThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    callback.response(apply(request));
                } catch (final Throwable t) {
                    callback.failure(t);
                }
            }
        });
    }

    @Override
    public String getName() {
        return "Apache HttpClient " + release;
    }

    @Override
    public void close() {
        try {
            client.close();
        } catch (final IOException e) {
            throw new ProcessingException(LocalizationMessages.FAILED_TO_STOP_CLIENT(), e);
        }
    }

    private HttpHost getHost(final HttpUriRequest request) {
        return new HttpHost(request.getURI().getHost(), request.getURI().getPort(), request.getURI().getScheme());
    }

    private HttpUriRequest getUriHttpRequest(final ClientRequest clientRequest) {
        final RequestConfig.Builder requestConfigBuilder = RequestConfig.copy(requestConfig);

        final int connectTimeout = clientRequest.resolveProperty(ClientProperties.CONNECT_TIMEOUT, -1);
        final int socketTimeout = clientRequest.resolveProperty(ClientProperties.READ_TIMEOUT, -1);

        if (connectTimeout >= 0) {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
        }
        if (socketTimeout >= 0) {
            requestConfigBuilder.setSocketTimeout(socketTimeout);
        }

        final Boolean redirectsEnabled =
                clientRequest.resolveProperty(ClientProperties.FOLLOW_REDIRECTS, requestConfig.isRedirectsEnabled());
        requestConfigBuilder.setRedirectsEnabled(redirectsEnabled);

        final Boolean bufferingEnabled = clientRequest.resolveProperty(ClientProperties.REQUEST_ENTITY_PROCESSING,
                RequestEntityProcessing.class) == RequestEntityProcessing.BUFFERED;
        final HttpEntity entity = getHttpEntity(clientRequest, bufferingEnabled);

        return RequestBuilder
                .create(clientRequest.getMethod())
                .setUri(clientRequest.getUri())
                .setConfig(requestConfigBuilder.build())
                .setEntity(entity)
                .build();
    }

    private HttpEntity getHttpEntity(final ClientRequest clientRequest, final boolean bufferingEnabled) {
        final Object entity = clientRequest.getEntity();

        if (entity == null) {
            return null;
        }

        final AbstractHttpEntity httpEntity = new AbstractHttpEntity() {
            @Override
            public boolean isRepeatable() {
                return false;
            }

            @Override
            public long getContentLength() {
                return -1;
            }

            @Override
            public InputStream getContent() throws IOException, IllegalStateException {
                if (bufferingEnabled) {
                    final ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
                    writeTo(buffer);
                    return new ByteArrayInputStream(buffer.toByteArray());
                } else {
                    return null;
                }
            }

            @Override
            public void writeTo(final OutputStream outputStream) throws IOException {
                clientRequest.setStreamProvider(new OutboundMessageContext.StreamProvider() {
                    @Override
                    public OutputStream getOutputStream(final int contentLength) throws IOException {
                        return outputStream;
                    }
                });
                clientRequest.writeEntity();
            }

            @Override
            public boolean isStreaming() {
                return false;
            }
        };

        if (bufferingEnabled) {
            try {
                return new BufferedHttpEntity(httpEntity);
            } catch (final IOException e) {
                throw new ProcessingException(LocalizationMessages.ERROR_BUFFERING_ENTITY(), e);
            }
        } else {
            return httpEntity;
        }
    }

    private static Map<String, String> writeOutBoundHeaders(final MultivaluedMap<String, Object> headers,
                                                            final HttpUriRequest request) {
        final Map<String, String> stringHeaders = HeaderUtils.asStringHeadersSingleValue(headers);

        for (final Map.Entry<String, String> e : stringHeaders.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }
        return stringHeaders;
    }

    private static final class HttpClientResponseInputStream extends FilterInputStream {

        HttpClientResponseInputStream(final InputStream inputStream) throws IOException {
            super(inputStream);
        }

        @Override
        public void close() throws IOException {
            super.close();
        }
    }

    private static InputStream getInputStream(final CloseableHttpResponse response) throws IOException {

        if (response.getEntity() == null) {
            return new ByteArrayInputStream(new byte[0]);
        } else {
            final InputStream i = response.getEntity().getContent();
            if (i.markSupported()) {
                return i;
            }
            return new BufferedInputStream(i, ReaderWriter.BUFFER_SIZE);
        }
    }

    private static final class ConnectionFactory extends ManagedHttpClientConnectionFactory {

        private static final AtomicLong COUNTER = new AtomicLong();

        private final int chunkSize;

        private ConnectionFactory(final int chunkSize) {
            this.chunkSize = chunkSize;
        }

        @Override
        public ManagedHttpClientConnection create(final HttpRoute route, final ConnectionConfig config) {
            final String id = "http-outgoing-" + Long.toString(COUNTER.getAndIncrement());

            return new HttpClientConnection(id, config.getBufferSize(), chunkSize);
        }
    }

    private static final class HttpClientConnection extends DefaultManagedHttpClientConnection {

        private final int chunkSize;

        private HttpClientConnection(final String id, final int buffersize, final int chunkSize) {
            super(id, buffersize);

            this.chunkSize = chunkSize;
        }

        @Override
        protected OutputStream createOutputStream(final long len, final SessionOutputBuffer outbuffer) {
            if (len == ContentLengthStrategy.CHUNKED) {
                return new ChunkedOutputStream(chunkSize, outbuffer);
            }
            return super.createOutputStream(len, outbuffer);
        }
    }
}
