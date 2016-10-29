package com.woyao.customer.disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.woyao.customer.chat.session.SessionContainer;
import com.woyao.customer.dto.chat.out.Outbound;
import com.woyao.customer.service.IChatService;

public class ChatMsgEventHandler extends AbstractEventHandler<ChatMsgEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "chatService")
	private IChatService chatService;

	@Resource(name = "sessionContainer")
	private SessionContainer sessionContainer;

	@Override
	protected void doTask(ChatMsgEvent event, long sequence, boolean endOfBatch) {
		WebSocketSession wsSession = sessionContainer.getSession(event.getToSessionId());
		this.sendMsg(event.getOutbound(), wsSession);
	}

	private boolean sendMsg(Outbound outbound, WebSocketSession wsSession) {
		if (wsSession == null) {
			logger.error(" Failed to send msg! Target WebSocketSession is null!");
			return false;
		}
		if (!wsSession.isOpen()) {
			logger.error(" Failed to send msg! Target WebSocketSession is closed!");
			return false;
		}
		long start = System.currentTimeMillis();
		try {
			logger.debug("Send out: \n{}", outbound.getContent());
			outbound.send(wsSession);
			return true;
		} catch (Exception e) {
			String msg = String.format("Msg send to session: %s failure!\n%s", wsSession.getId(), outbound.getContent());
			logger.error(msg, e);
		} finally {
			long spent = System.currentTimeMillis() - start;
			this.sendSt.add(spent);
		}
		return false;
	}

	private SendStInfo sendSt = new SendStInfo();

	private class SendStInfo {
		private long sentMsgNum = 0L;
		private long avgSpent = 0L;
		private List<Long> spentLogs = new ArrayList<>();

		private void calcStatis() {
			List<Long> spents = this.spentLogs;
			this.spentLogs = new ArrayList<>();
			this.sentMsgNum = spents.size();
			if (this.sentMsgNum <= 0) {
				return;
			}
			long total = 0L;
			for (long l : spents) {
				total += l;
			}
			this.avgSpent = total / this.sentMsgNum;
			logger.warn(this.toString());
		}

		private synchronized void add(long spent) {
			this.spentLogs.add(spent);
			if (this.spentLogs.size() >= 10000) {
				this.calcStatis();
			}
		}

		@Override
		public String toString() {
			return "SendStInfo [sentMsgNum=" + sentMsgNum + ", avgSpent=" + avgSpent + "]";
		}

	}
}
