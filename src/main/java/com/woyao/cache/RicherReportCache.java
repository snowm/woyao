package com.woyao.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;

import com.woyao.customer.dto.ProfileDTO;
import com.woyao.customer.dto.RicherDTO;

@Component("richerReportCache")
public class RicherReportCache {

	private Map<Long, ConcurrentHashMap<Long, AtomicInteger>> dailyRichers = new ConcurrentHashMap<>();

	private RicherComparator richerComparator = new RicherComparator();

	/**
	 * 每天定时清理土豪统计
	 */
	public void refreshDaily() {
		dailyRichers.clear();
	}

	/**
	 * 有土豪成功弄了新订单
	 * 
	 * @param shopId
	 * @param chatterId
	 */
	public void newOrder(long shopId, long chatterId) {
		ConcurrentHashMap<Long, AtomicInteger> shopRichers = this.dailyRichers.computeIfAbsent(shopId, e -> {
			return new ConcurrentHashMap<>();
		});
		AtomicInteger count = shopRichers.computeIfAbsent(chatterId, e -> {
			return new AtomicInteger(0);
		});
		count.getAndIncrement();
	}

	/**
	 * 列出店铺的当日土豪
	 * 
	 * @param shopId
	 * @return
	 */
	public List<RicherDTO> listDailyRichers(long shopId) {
		Map<Long, AtomicInteger> richers = this.dailyRichers.get(shopId);
		List<RicherDTO> rs = new ArrayList<>();
		if (richers == null || richers.isEmpty()) {
			return rs;
		}
		richers.forEach((k, v) -> {
			RicherDTO r = new RicherDTO();
			r.setPayMsgCount(v.get());
			ProfileDTO chatterDTO = new ProfileDTO();
			chatterDTO.setId(k);
			r.setChatterDTO(chatterDTO);
		});
		Collections.sort(rs, richerComparator);
		return rs;
	}

	/**
	 * 当应用启动时，初始化加载土豪统计，将已有的统计加入此cache
	 * 
	 * @param shopId
	 * @param richers
	 */
	public void loadReport(long shopId, Map<Long, Integer> richers) {
		ConcurrentHashMap<Long, AtomicInteger> shopRichers = this.dailyRichers.computeIfAbsent(shopId, e -> {
			return new ConcurrentHashMap<>();
		});
		richers.forEach((k, v) -> {
			shopRichers.put(k, new AtomicInteger(v));
		});
	}

	private class RicherComparator implements Comparator<RicherDTO> {

		@Override
		public int compare(RicherDTO o1, RicherDTO o2) {
			return -(o1.getPayMsgCount() - o2.getPayMsgCount());
		}

	}

}
