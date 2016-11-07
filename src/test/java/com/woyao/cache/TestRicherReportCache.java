package com.woyao.cache;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import com.woyao.customer.dto.RicherDTO;

public class TestRicherReportCache {

	@Test
	public void testSort() {
		List<RicherDTO> rs = new ArrayList<>();
		RicherDTO r = new RicherDTO();
		r.setPayMsgCount(2);
		rs.add(r);

		r = new RicherDTO();
		r.setPayMsgCount(1);
		rs.add(r);

		r = new RicherDTO();
		r.setPayMsgCount(1);
		rs.add(r);

		r = new RicherDTO();
		r.setPayMsgCount(2);
		rs.add(r);
		
		r = new RicherDTO();
		r.setPayMsgCount(3);
		rs.add(r);
		
		Collections.sort(rs, new RicherComparator());
		RicherDTO d = rs.get(0);
		assertSame(3, d.getPayMsgCount());

		d = rs.get(1);
		assertSame(2, d.getPayMsgCount());

		d = rs.get(2);
		assertSame(2, d.getPayMsgCount());

		d = rs.get(3);
		assertSame(1, d.getPayMsgCount());
		
		d = rs.get(4);
		assertSame(1, d.getPayMsgCount());
	}

	private class RicherComparator implements Comparator<RicherDTO> {

		@Override
		public int compare(RicherDTO o1, RicherDTO o2) {
			return -(o1.getPayMsgCount() - o2.getPayMsgCount());
		}

	}
}
