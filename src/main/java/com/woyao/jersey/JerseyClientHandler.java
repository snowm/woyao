package com.woyao.jersey;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.spi.AsyncConnectorCallback;
import org.glassfish.jersey.netty.connector.internal.NettyInputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import jersey.repackaged.com.google.common.util.concurrent.SettableFuture;

/**
 * Jersey implementation of Netty channel handler.
 *
 * @author Pavel Bucek (pavel.bucek at oracle.com)
 */
class JerseyClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final SnowmNettyConnector connector;
    private final LinkedBlockingDeque<InputStream> isList = new LinkedBlockingDeque<>();

    private final AsyncConnectorCallback asyncConnectorCallback;
    private final ClientRequest jerseyRequest;
    private final SettableFuture future;

    JerseyClientHandler(SnowmNettyConnector nettyConnector, ClientRequest request,
                        AsyncConnectorCallback callback, SettableFuture future) {
        this.connector = nettyConnector;
        this.asyncConnectorCallback = callback;
        this.jerseyRequest = request;
        this.future = future;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof HttpResponse) {
            final HttpResponse response = (HttpResponse) msg;

            final ClientResponse jerseyResponse = new ClientResponse(new Response.StatusType() {
                @Override
                public int getStatusCode() {
                    return response.status().code();
                }

                @Override
                public Response.Status.Family getFamily() {
                    return Response.Status.Family.familyOf(response.status().code());
                }

                @Override
                public String getReasonPhrase() {
                    return response.status().reasonPhrase();
                }
            }, jerseyRequest);

            for (Map.Entry<String, String> entry : response.headers().entries()) {
                jerseyResponse.getHeaders().add(entry.getKey(), entry.getValue());
            }

            // request entity handling.
            if ((response.headers().contains(HttpHeaderNames.CONTENT_LENGTH) && HttpUtil.getContentLength(response) > 0)
                    || HttpUtil.isTransferEncodingChunked(response)) {

                ctx.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        isList.add(NettyInputStream.END_OF_INPUT_ERROR);
                    }
                });

                jerseyResponse.setEntityStream(new NettyInputStream(isList));
            } else {
                jerseyResponse.setEntityStream(new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return -1;
                    }
                });
            }

            if (asyncConnectorCallback != null) {
                connector.executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        asyncConnectorCallback.response(jerseyResponse);
                        future.set(jerseyResponse);
                    }
                });
            }

        }
        if (msg instanceof HttpContent) {

            HttpContent httpContent = (HttpContent) msg;

            ByteBuf content = httpContent.content();

            if (content.isReadable()) {
                // copy bytes - when netty reads last chunk, it automatically closes the channel, which invalidates all
                // relates ByteBuffs.
                byte[] bytes = new byte[content.readableBytes()];
                content.getBytes(content.readerIndex(), bytes);
                isList.add(new ByteArrayInputStream(bytes));
            }

            if (msg instanceof LastHttpContent) {
                isList.add(NettyInputStream.END_OF_INPUT);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, final Throwable cause) {
        if (asyncConnectorCallback != null) {
            connector.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    asyncConnectorCallback.failure(cause);
                }
            });
        }
        future.setException(cause);
        isList.add(NettyInputStream.END_OF_INPUT_ERROR);
    }
}