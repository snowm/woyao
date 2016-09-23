package com.snowm.cat.config;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.JstlView;

import com.snowm.cat.interceptor.LogInterceptor;
import com.snowm.security.web.exception.SnowmHandlerExceptionResolver;

@Configuration
@EnableWebMvc
@Order(2)
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	@Bean(name = "cacheControlLib")
	public CacheControl resourceLibCacheControl() {
		CacheControl cacheControl = CacheControl.maxAge(12, TimeUnit.HOURS).cachePrivate();
		return cacheControl;
	}
	@Bean(name = "cacheControlVersioned")
	public CacheControl resourceDynamicCacheControl() {
		CacheControl cacheControl = CacheControl.noCache().cachePrivate().mustRevalidate();
		return cacheControl;
	}

	@Bean(name = "jackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(){
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		return converter;
	}
	
	@Bean(name = "stringHttpMessageConverter")
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxUploadSize(env.getProperty("multipartParser.maxUploadSize", int.class, 2097152));
		resolver.setMaxUploadSizePerFile(env.getProperty("multipartParser.maxUploadSizePerFile", int.class, 204800));
		return resolver;
	}
	
	@Bean(name="logInterceptor")
	public HandlerInterceptorAdapter logInterceptor(){
		LogInterceptor interceptor = new LogInterceptor();
		return interceptor;
	}

	@Bean(name="snowmHandlerExceptionResolver")
	public  HandlerExceptionResolver snowmHandlerExceptionResolver() {
		SnowmHandlerExceptionResolver resolver = new SnowmHandlerExceptionResolver();
		return resolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor()).addPathPatterns("/ali/**", "/channelOrder/**", "/test/**");
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jackson2HttpMessageConverter());
		converters.add(stringHttpMessageConverter());
		converters.add(new Jaxb2RootElementHttpMessageConverter());
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.enableContentNegotiation(new JstlView());
		registry.jsp("/jsp/", ".jsp");
	}
	
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(snowmHandlerExceptionResolver());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		int cachePeriod = 3600 * 24 * 365;
		registry.addResourceHandler("/admin/resources/images/**", "/resources/images/**")
				.addResourceLocations("/resources/images/").setCachePeriod(cachePeriod);
		
		registry.addResourceHandler("/admin/resources/css/**", "/resources/css/**")
				.addResourceLocations("/resources/css/").setCacheControl(resourceLibCacheControl());
		
		registry.addResourceHandler("/admin/resources/js/lib/**", "/resources/js/lib/**")
				.addResourceLocations("/resources/js/lib/").setCacheControl(resourceLibCacheControl());
		
		registry.addResourceHandler("/admin/resources/js/bb/**", "/resources/js/bb/**")
				.addResourceLocations("/resources/js/bb/").setCacheControl(resourceDynamicCacheControl());
		
		registry.addResourceHandler("/admin/resources/js/base/**", "/resources/js/base/**")
				.addResourceLocations("/resources/js/base/").setCacheControl(resourceDynamicCacheControl());
		
		registry.addResourceHandler("/admin/resources/data/**", "/resources/data/**").addResourceLocations(
				"/resources/data/");
	}
}
