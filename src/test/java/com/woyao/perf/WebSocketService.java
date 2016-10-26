package com.woyao.perf;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket(maxTextMessageSize = 256 * 1024)
public class WebSocketService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private WebSocketClient client;
	private Integer error = 0;
	private CountDownLatch openLatch = new CountDownLatch(1);
	private CountDownLatch closeLatch = new CountDownLatch(1);
	private Session session = null;
	private AtomicLong recMsgCounter = new AtomicLong(0L);
	private boolean connected = false;

	public WebSocketService(WebSocketClient client) {
		this.client = client;
	}

	@OnWebSocketMessage
	public void onMessage(String msg) {
		log.debug("Received message: " + msg);
		recMsgCounter.incrementAndGet();
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		log.debug("Connect " + session.isOpen());
		this.session = session;
		connected = true;
		openLatch.countDown();
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		if (statusCode != 1000) {
			log.info("Disconnect " + statusCode + ": " + reason);
			error = statusCode;
		} else {
			log.info("Disconnect " + statusCode + ": " + reason);
		}

		// Notify connection opening and closing latches of the closed
		// connection
		openLatch.countDown();
		closeLatch.countDown();
		connected = false;
		this.session = null;
	}

	public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
		boolean res = this.closeLatch.await(duration, unit);

		return res;
	}

	public boolean awaitOpen(int duration, TimeUnit unit) throws InterruptedException {
		boolean res = this.openLatch.await(duration, unit);

		if (connected) {
			log.debug(" - Connection established");
		} else {
			log.error(" - Cannot connect to the remote server");
		}

		return res;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	public void sendMessage(String message) throws IOException {
		session.getRemote().sendString(message);
	}

	public void close() {
		close(StatusCode.NORMAL, "Client closed session.");
	}

	public void close(int statusCode, String statusText) {
		if (session != null) {
			session.close(statusCode, statusText);
		} 

		// Stoping WebSocket client; thanks m0ro
		try {
			client.stop();
			log.info(" - WebSocket client closed by the client");
		} catch (Exception e) {
			log.info(" - WebSocket client wasn't started (...that's odd", e);
		}
	}

	/**
	 * @return the error
	 */
	public Integer getError() {
		return error;
	}

	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}

	public long getRecMsgCount() {
		return this.recMsgCounter.get();
	}
}
