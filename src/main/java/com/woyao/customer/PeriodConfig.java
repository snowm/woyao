package com.woyao.customer;

import java.util.Calendar;
import java.util.Date;

public abstract class PeriodConfig {
	
	private static final int DAILY_HOUR_START = 8;

	public static Date getDailyStartDt(Calendar now) {
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTimeInMillis(now.getTimeInMillis());
		dayStart.set(Calendar.HOUR_OF_DAY, DAILY_HOUR_START);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		dayStart.set(Calendar.MILLISECOND, 0);

		if (now.before(dayStart)) {
			dayStart.add(Calendar.DAY_OF_MONTH, -1);
		}
		return dayStart.getTime();
	}

	public static Date getDailyEndDt(Calendar now) {
		Calendar dayEnd = Calendar.getInstance();
		dayEnd.setTimeInMillis(now.getTimeInMillis());
		dayEnd.set(Calendar.HOUR_OF_DAY, DAILY_HOUR_START);
		dayEnd.set(Calendar.MINUTE, 0);
		dayEnd.set(Calendar.SECOND, 0);
		dayEnd.set(Calendar.MILLISECOND, 0);

		if (now.after(dayEnd)) {
			dayEnd.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dayEnd.getTime();
	}

	public static Date getDailyStartDt() {
		return getDailyStartDt(Calendar.getInstance());
	}

	public static Date getDailyEndDt() {
		return getDailyEndDt(Calendar.getInstance());
	}

	public static Date getWeekStartDt(Calendar now) {
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTimeInMillis(now.getTimeInMillis());
		dayStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		dayStart.set(Calendar.HOUR_OF_DAY, DAILY_HOUR_START);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		dayStart.set(Calendar.MILLISECOND, 0);

		if (now.before(dayStart)) {
			dayStart.add(Calendar.WEEK_OF_MONTH, -1);
		}
		return dayStart.getTime();
	}

	public static Date getWeekEndDt(Calendar now) {
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTimeInMillis(now.getTimeInMillis());
		dayStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		dayStart.set(Calendar.HOUR_OF_DAY, DAILY_HOUR_START);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		dayStart.set(Calendar.MILLISECOND, 0);

		if (now.after(dayStart)) {
			dayStart.add(Calendar.WEEK_OF_MONTH, 1);
		}
		return dayStart.getTime();
	}

	public static Date getWeekStartDt() {
		return getWeekStartDt(Calendar.getInstance());
	}

	public static Date getWeekEndDt() {
		return getWeekEndDt(Calendar.getInstance());
	}
	

	public static Date getMonthStartDt(Calendar now) {
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTimeInMillis(now.getTimeInMillis());
		dayStart.set(Calendar.DAY_OF_MONTH, 1);
		dayStart.set(Calendar.HOUR_OF_DAY, DAILY_HOUR_START);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		dayStart.set(Calendar.MILLISECOND, 0);

		if (now.before(dayStart)) {
			dayStart.add(Calendar.MONTH, -1);
		}
		return dayStart.getTime();
	}

	public static Date getMonthEndDt(Calendar now) {
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTimeInMillis(now.getTimeInMillis());
		dayStart.set(Calendar.DAY_OF_MONTH, 1);
		dayStart.set(Calendar.HOUR_OF_DAY, DAILY_HOUR_START);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		dayStart.set(Calendar.MILLISECOND, 0);

		if (now.after(dayStart)) {
			dayStart.add(Calendar.MONTH, 1);
		}
		return dayStart.getTime();
	}

	public static Date getMonthStartDt() {
		return getMonthStartDt(Calendar.getInstance());
	}

	public static Date getMonthEndDt() {
		return getMonthEndDt(Calendar.getInstance());
	}
	
}
