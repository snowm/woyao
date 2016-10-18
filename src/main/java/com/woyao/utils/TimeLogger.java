package com.woyao.utils;

public class TimeLogger {

	private long startTime;
	private long endTime;
	private long spent;
	private boolean ended;

	private TimeLogger() {
		super();
	}

	public static TimeLogger newLogger() {
		TimeLogger logger = new TimeLogger();
		return logger;
	}

	public TimeLogger start() {
		this.startTime = System.currentTimeMillis();
		this.ended = false;
		return this;
	}

	public TimeLogger end() {
		this.endTime = System.currentTimeMillis();
		this.spent = this.endTime - this.startTime;
		this.ended = true;
		return this;
	}

	public long spent() {
		if (!this.ended) {
			throw new IllegalStateException("Time logger is not end!");
		}
		return this.spent;
	}
}
