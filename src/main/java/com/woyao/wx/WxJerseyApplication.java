package com.woyao.wx;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class WxJerseyApplication extends ResourceConfig{

	public WxJerseyApplication(){
		this.register(RequestContextFilter.class);
//		Logger logger = new Log4jLogger("JerseyServerLogging", null);
//		LoggingFeature loggingFeature = new LoggingFeature(logger);
		this.register(LoggingFeature.class);
		this.register(WxJerseyService.class);
	}
}
