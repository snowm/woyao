package com.woyao;

public class TimeLogger {

	private long startTime;
	private long endTime;
	private String name;

	private TimeLogger() {
		super();
	}

	private TimeLogger(String name) {
		super();
		this.name = name;
	}

	public static TimeLogger start(String name) {
		TimeLogger logger = new TimeLogger(name);
		logger.internalStart();
		return logger;
	}

	private void internalStart() {
		this.startTime = System.currentTimeMillis();
		System.out.println(name + " started at:" + startTime);
	}

	public void end() {
		this.endTime = System.currentTimeMillis();
		System.out.println(name + " end at:" + endTime + ", spent " + (this.endTime - this.startTime));
	}
}
