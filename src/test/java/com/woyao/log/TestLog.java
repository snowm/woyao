package com.woyao.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TestLog {

	private Log logger = LogFactory.getLog(TestLog.class);

	@Test
	public void testLog(){
		logger.debug("debug message");
		logger.info("info message");
		logger.warn("warn message");
		logger.error("error message");
	}
}
