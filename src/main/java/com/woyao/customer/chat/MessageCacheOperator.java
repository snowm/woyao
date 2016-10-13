package com.woyao.customer.chat;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.springframework.stereotype.Component;

import com.woyao.customer.dto.chat.BlockDTO;
import com.woyao.customer.dto.chat.in.InMsg;
import com.woyao.customer.dto.chat.in.InMsgBlockDTO;
import com.woyao.customer.dto.chat.in.InMsgDTO;
import com.woyao.customer.dto.chat.in.Inbound;

@Component("messageCacheOperator")
public class MessageCacheOperator {

	private BlockComparator blockComparator = new BlockComparator();

	public InMsg receiveMsg(Lock lock, Map<Long, InMsg> cache, Inbound inbound) {
		if (inbound instanceof InMsgDTO) {
			return this.processMessage(lock, cache, InMsgDTO.class.cast(inbound));
		} else if (inbound instanceof InMsgBlockDTO) {
			return this.processMessage(lock, cache, InMsgBlockDTO.class.cast(inbound));
		}
		return null;
	}

	private InMsg processMessage(Lock lock, Map<Long, InMsg> cache, InMsgDTO msg) {
		try {
			Long msgId = msg.getMsgId();
			lock.lock();
			InMsg rs = cache.getOrDefault(msgId, new InMsg());

			rs.setMsgId(msgId);
			rs.setProductId(msg.getProductId());
			rs.setTo(msg.getTo());
			rs.setBlockSize(msg.getBlockSize());
			rs.getBlocks().add(msg.getBlock());

			if (this.checkMsgIntegrity(rs)) {
				cache.remove(msgId);
				return this.assembleMsg(rs);
			}

			cache.put(msgId, rs);
			return null;
		} finally {
			lock.unlock();
		}
	}

	public InMsg processMessage(Lock lock, Map<Long, InMsg> cache, InMsgBlockDTO block) {
		try {
			Long msgId = block.getMsgId();
			lock.lock();
			InMsg rs = cache.getOrDefault(msgId, new InMsg());

			rs.setMsgId(msgId);
			rs.getBlocks().add(block.getBlock());

			if (this.checkMsgIntegrity(rs)) {
				cache.remove(msgId);
				return this.assembleMsg(rs);
			}

			cache.put(msgId, rs);
			return null;
		} finally {
			lock.unlock();
		}
	}

	private boolean checkMsgIntegrity(InMsg msg) {
		int size = msg.getBlockSize();
		int actualSize = msg.getBlocks().size();
		return size == actualSize;
	}

	private InMsg assembleMsg(InMsg msg) {
		Collections.sort(msg.getBlocks(), blockComparator);
		return msg;
	}

	private class BlockComparator implements Comparator<BlockDTO> {

		@Override
		public int compare(BlockDTO o1, BlockDTO o2) {
			return o1.getSeq() - o2.getSeq();
		}

	}
}
