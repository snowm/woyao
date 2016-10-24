package com.woyao.customer.chat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.woyao.customer.dto.chat.BlockDTO;
import com.woyao.customer.dto.chat.in.EntireInMsg;
import com.woyao.customer.dto.chat.in.ChatMsgBlockDTO;
import com.woyao.customer.dto.chat.in.ChatMsgDTO;
import com.woyao.customer.dto.chat.in.Inbound;

@Component("messageCacheOperator")
public class MessageCacheOperator {

	private BlockComparator blockComparator = new BlockComparator();

	public EntireInMsg receiveMsg(Lock lock, Map<Long, EntireInMsg> cache, Inbound inbound) {
		if (inbound instanceof ChatMsgDTO) {
			return this.processMessage(lock, cache, ChatMsgDTO.class.cast(inbound));
		} else if (inbound instanceof ChatMsgBlockDTO) {
			return this.processMessage(lock, cache, ChatMsgBlockDTO.class.cast(inbound));
		}
		return null;
	}

	private EntireInMsg processMessage(Lock lock, Map<Long, EntireInMsg> cache, ChatMsgDTO msg) {
		try {
			Long msgId = msg.getMsgId();
			EntireInMsg rs = cache.computeIfAbsent(msgId, key->new EntireInMsg());
			lock.lock();

			if (rs.getMsgId() != null) {
				rs = new EntireInMsg();
				cache.put(msgId, rs);
			}
			BeanUtils.copyProperties(msg, rs);

			this.addBlock(rs.getBlocks(), msg.getBlock());

			if (this.checkMsgIntegrity(rs)) {
				cache.remove(msgId);
				return this.assembleMsg(rs);
			}

			return null;
		} finally {
			lock.unlock();
		}
	}

	private EntireInMsg processMessage(Lock lock, Map<Long, EntireInMsg> cache, ChatMsgBlockDTO block) {
		try {
			Long msgId = block.getMsgId();
			EntireInMsg rs = cache.computeIfAbsent(msgId, key->new EntireInMsg());
			lock.lock();

			this.addBlock(rs.getBlocks(), block.getBlock());

			if (this.checkMsgIntegrity(rs)) {
				cache.remove(msgId);
				return this.assembleMsg(rs);
			}

			return null;
		} finally {
			lock.unlock();
		}
	}

	private void addBlock(List<BlockDTO> blocks, BlockDTO block){
		blocks.add(block);
	}
	
	/**
	 * 检查消息完整性
	 * @param msg
	 * @return
	 */
	private boolean checkMsgIntegrity(EntireInMsg msg) {
		int size = msg.getBlockSize();
		int actualSize = msg.getBlocks().size();
		return size == actualSize;
	}

	private EntireInMsg assembleMsg(EntireInMsg msg) {
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
