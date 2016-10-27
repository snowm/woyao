package com.woyao.perf;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woyao.customer.dto.chat.BlockDTO;
import com.woyao.customer.dto.chat.in.ChatMsgDTO;
import com.woyao.utils.JsonUtils;
import com.woyao.utils.TimeLogger;

public class TestWebSocket {

	private static final int totalProfiles = 200;

	private static final int durationTime = 60;

	private static final int msgInterval = 10 * 1000;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private ExecutorService executor = Executors.newFixedThreadPool(totalProfiles);

	private int connectionTimeout = 3;
	
	private URI uri = null;

	private String testNode = "1";

	public TestWebSocket() throws URISyntaxException {
		uri = new URI("ws", null, "www.luoke30.com", 80, "/mobile/chat/socket", null, null);
	}

	public static void main(String[] args) throws URISyntaxException {
		TestWebSocket t = new TestWebSocket();
		t.testWebSocket();
	}

	public void testWebSocket() {
		AtomicLong idg = new AtomicLong(0L);
		AtomicLong successLog = new AtomicLong(0L);
		AtomicLong failLog = new AtomicLong(0L);

		List<WebSocketService> services = new ArrayList<>();
		TimeLogger tl_a = TimeLogger.newLogger().start();
		TimeLogger tl = TimeLogger.newLogger().start();
		for (int i = 0; i < totalProfiles; i++) {
			services.add(this.createService());
		}
		logger.info("Create connections spent: {}", tl.end().spent());
		List<Future<Void>> futures = new ArrayList<>();
		for (int i = 0; i < totalProfiles; i++) {
			TestTask task = new TestTask(services.get(i), i, idg, this.testNode);
			task.successLog = successLog;
			task.failLog = failLog;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			futures.add(this.executor.submit(task));
		}
		this.executor.shutdown();
		try {
			this.executor.awaitTermination(durationTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Future<Void> f : futures) {
			f.cancel(true);
		}
		long recievedMsgCount = 0;
		for (WebSocketService s : services) {
			recievedMsgCount += s.getRecMsgCount();
		}
		logger.info("总共{}用户，{}毫秒, 共发送{}消息:成功{}, 失败{}; 共接收{}消息", totalProfiles, tl_a.end().spent(), idg.get(), successLog.get(),
				failLog.get(), recievedMsgCount);
	}

	private class TestTask implements Callable<Void> {

		int clientNumber;

		AtomicLong msgIdGenerator;

		WebSocketService service;

		String testNode;

		boolean run = true;

		AtomicLong successLog;

		AtomicLong failLog;

		TestTask(WebSocketService service, int clientNumber, AtomicLong msgIdGenerator, String testNode) {
			this.service = service;
			this.clientNumber = clientNumber;
			this.msgIdGenerator = msgIdGenerator;
			this.testNode = testNode;
		}

		@Override
		public Void call() throws Exception {
			int cn = this.clientNumber;
			AtomicLong idg = this.msgIdGenerator;
			WebSocketService sv = this.service;
			String tn = this.testNode;
			AtomicLong slog = this.successLog;
			AtomicLong flog = this.failLog;

			try {
				while (run) {
					long msgId = idg.getAndIncrement();
					try {
						this.sendMsg(tn, cn, msgId, sv);
						slog.incrementAndGet();
					} catch (Exception ex) {
						flog.incrementAndGet();
					}
					if (Thread.interrupted()) {
						this.run = false;
						continue;
					}
					Thread.sleep(msgInterval);
				}
			} finally {
				sv.close();
			}
			return null;
		}

		private void sendMsg(String tn, int cn, long msgId, WebSocketService sv) throws IOException {
			String msgContent = generateMsgContent(tn, cn, msgId);
			sv.sendMessage(msgContent);
		}

		private String generateMsgContent(String tn, int clientNumber, long msgId) {
			StringBuilder sb = new StringBuilder();
			sb.append("msg\n");
			ChatMsgDTO msg = new ChatMsgDTO();
			BlockDTO block = new BlockDTO();
			block.setSeq(0);
			String blockContent = String.format(
					"{\"text\":\"lkjkasfiopaslk;jalkjsfko329y5hkjhda kjl89uwk3 ----- %s ------testNode:%s ,clientNumber:%s, msgId:%s\"}",
					UUID.randomUUID().toString(), tn, clientNumber, msgId);
			block.setBlock(blockContent);
			msg.setBlock(block);
			msg.setBlockSize(1);
			msg.setMsgId(msgId);
			try {
				sb.append(JsonUtils.toString(msg));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}

	}

	ExecutorService es = Executors.newCachedThreadPool();
	WebSocketClient client = new WebSocketClient(es);

	private WebSocketService createService() {
		// ExecutorService executor = Executors.newFixedThreadPool(4);
		WebSocketService service = new WebSocketService(client);
		// Start WebSocket client thread and upgrage HTTP connection
		try {
			this.client.start();
			ClientUpgradeRequest request = new ClientUpgradeRequest();
			this.client.connect(service, this.uri, request);
			service.awaitOpen(connectionTimeout, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return service;
	}
}
