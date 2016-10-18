package com.woyao.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

	private Logger logger = LoggerFactory.getLogger(TestLog.class);

	@Test
	public void testLog(){
		logger.debug("debug message");
		logger.info("info message");
		logger.warn("warn message");
		logger.error("error message");
	}
}
