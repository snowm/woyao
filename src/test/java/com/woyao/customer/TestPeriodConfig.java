package com.woyao.customer;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TestPeriodConfig {

	@Test
	public void testGetDailyStart_LastDay() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 7);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getDailyStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.add(Calendar.DAY_OF_MONTH, -1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetDailyStart_ThisDay() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getDailyStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetDailyEnd_ThisDay() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 7);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getDailyEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetDailyEnd_NextDay() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getDailyEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.add(Calendar.DAY_OF_MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekStart_LastWeek1() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		now.set(Calendar.HOUR_OF_DAY, 7);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		expected.add(Calendar.WEEK_OF_MONTH, -1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekStart_LastWeek2() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		expected.add(Calendar.WEEK_OF_MONTH, -1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekStart_ThisWeek1() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekStart_ThisWeek2() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekEnd_ThisWeek1() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		now.set(Calendar.HOUR_OF_DAY, 7);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekEnd_ThisWeek2() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekEnd_NextWeek1() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		expected.add(Calendar.WEEK_OF_MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetWeekEnd_NextWeek2() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getWeekEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		expected.add(Calendar.WEEK_OF_MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetMonthStart_LastMonth() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 7);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getMonthStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_MONTH, 1);
		expected.add(Calendar.MONTH, -1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetMonthStart_ThisMonth1() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getMonthStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetMonthStart_ThisMonth2() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.add(Calendar.DAY_OF_MONTH, -1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getMonthStartDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetMonthEnd_ThisMonth1() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 7);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getMonthEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetMonthEnd_ThisMonth2() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 1);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getMonthEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetMonthEnd_NextMonth1() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 9);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getMonthEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_MONTH, 1);
		expected.add(Calendar.MONTH, 1);

		assertEquals(expected.getTime(), st);
	}

	@Test
	public void testGetMonthEnd_NextMonth2() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.add(Calendar.DAY_OF_MONTH, -1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date st = PeriodConfig.getMonthEndDt(now);

		Calendar expected = Calendar.getInstance();
		expected.setTimeInMillis(now.getTimeInMillis());

		expected.set(Calendar.HOUR_OF_DAY, 8);
		expected.set(Calendar.DAY_OF_MONTH, 1);
		expected.add(Calendar.MONTH, 1);

		assertEquals(expected.getTime(), st);
	}
}
