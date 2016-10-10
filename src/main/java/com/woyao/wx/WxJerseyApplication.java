package com.woyao.wx;

import java.util.logging.Logger;

import org.apache.cxf.common.logging.Log4jLogger;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class WxJerseyApplication extends ResourceConfig{

	public WxJerseyApplication(){
		this.register(RequestContextFilter.class);
		Logger logger = new Log4jLogger("JerseyServerLogging", null);
		LoggingFeature loggingFeature = new LoggingFeature(logger);
		this.register(loggingFeature);
		this.register(WxService.class);
	}
}
