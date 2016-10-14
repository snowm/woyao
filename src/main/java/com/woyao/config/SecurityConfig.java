package com.woyao.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.snowm.security.core.userdetails.AuthService;
import com.snowm.security.core.userdetails.AuthServiceImpl;
import com.snowm.security.profile.service.PermissionService;
import com.snowm.security.profile.service.ProfileService;
import com.snowm.security.web.authentication.JsonAuthenticationFailureHandler;
import com.snowm.security.web.authentication.SSLAuthenticationSuccessHandler;
import com.snowm.security.web.authentication.SnowmAuthenticationEntryPoint;
import com.snowm.security.web.authentication.SnowmConcurrentSessionFilter;
import com.snowm.security.web.authentication.dao.DefaultAuthenticationProvider;
import com.snowm.security.web.matcher.CsrfRequestMatcher;

@Configuration
@Order(1)
public class SecurityConfig {

	private static final String LOGIN_PAGE = "/loginPage.html";

	@Resource(name = "defaultProfileService")
	private ProfileService profileService;

	@Resource(name = "defaultPermissionService")
	private PermissionService permissionService;

	@Bean(name = "woyaoPasswordEncoder")
	public PasswordEncoder myPasswordEncoder(@Value("${passwordEncoder.secret}") String secret) {
		PasswordEncoder encoder = new StandardPasswordEncoder(secret);
		return encoder;
	}

	@Bean(name = "woyaoAuthService")
	public AuthService defaultAuthService(
			@Qualifier("woyaoPasswordEncoder") PasswordEncoder passwordEncoder) {
		AuthServiceImpl authService = new AuthServiceImpl();
		authService.setPermissionService(this.permissionService);
		authService.setProfileService(this.profileService);
		authService.setPasswordEncoder(passwordEncoder);
		return authService;
	}

	@Bean(name = "woyaoAuthenticationProvider")
	public AuthenticationProvider authenticationProvider(
			@Qualifier("woyaoAuthService") AuthService authService,
			@Qualifier("woyaoPasswordEncoder") PasswordEncoder passwordEncoder) {
		DefaultAuthenticationProvider authenticationProvider = new DefaultAuthenticationProvider();
		authenticationProvider.setUserDetailsService(authService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		authenticationProvider.setLoginableAuthorites(null);
		return authenticationProvider;
	}

	@Bean(name = "sslAuthenticationSuccessHandler")
	public AuthenticationSuccessHandler successHandler() {
		SSLAuthenticationSuccessHandler handler = new SSLAuthenticationSuccessHandler();
		handler.setShareSecureOrNonSecureCookie(true);
		handler.setDefaultTargetUrl("/admin");
		handler.setAlwaysUseDefaultTargetUrl(true);
		return handler;
	}

	@Bean(name = "jsonAuthenticationFailureHandler")
	public AuthenticationFailureHandler failureHandler() {
		JsonAuthenticationFailureHandler handler = new JsonAuthenticationFailureHandler();
		handler.setDefaultFailureUrl(LOGIN_PAGE);
		return handler;
	}

	@Bean(name = "authenticationEntryPoint")
	public AuthenticationEntryPoint authenticationEntryPoint() {
		SnowmAuthenticationEntryPoint bean = new SnowmAuthenticationEntryPoint(LOGIN_PAGE);
		return bean;
	}

	@Bean(name = "accessDeniedHandler")
	public AccessDeniedHandler accessDeniedHandler() {
		return null;
	}

	@Bean(name = "sessionRegistry")
	public SessionRegistry sessionRegistry() {
		SessionRegistry bean = new SessionRegistryImpl();
		return bean;
	}

	@Bean(name = "concurrentSessionFilter")
	public Filter concurrentSessionFilter() {
		SnowmConcurrentSessionFilter bean = new SnowmConcurrentSessionFilter(sessionRegistry(), LOGIN_PAGE);
		return bean;
	}

	@Bean(name = "usernamePasswordAuthenticationFilter")
	public Filter usernamePasswordAuthenticationFilter(
			@Qualifier("woyaoAuthenticationProvider") AuthenticationProvider authenticationProvider,
			@Qualifier("authenticationManager") ProviderManager authenticationManager) {
		UsernamePasswordAuthenticationFilter bean = new UsernamePasswordAuthenticationFilter();
		bean.setAuthenticationManager(authenticationManager);
		bean.setSessionAuthenticationStrategy(sessionStrategy());
		bean.setAuthenticationFailureHandler(failureHandler());
		bean.setAuthenticationSuccessHandler(successHandler());
		bean.setPostOnly(true);
		bean.setFilterProcessesUrl("/admin/login");
		return bean;
	}

	@Bean(name = "sessionStrategy")
	public SessionAuthenticationStrategy sessionStrategy() {
		List<SessionAuthenticationStrategy> strategies = new ArrayList<>();
		ConcurrentSessionControlAuthenticationStrategy concurrentSessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(
				sessionRegistry());
		concurrentSessionStrategy.setMaximumSessions(1);
		concurrentSessionStrategy.setExceptionIfMaximumExceeded(false);
		strategies.add(concurrentSessionStrategy);

		SessionFixationProtectionStrategy fixationStrategy = new SessionFixationProtectionStrategy();
		fixationStrategy.setMigrateSessionAttributes(true);
		strategies.add(fixationStrategy);

		strategies.add(new RegisterSessionAuthenticationStrategy(sessionRegistry()));

		CompositeSessionAuthenticationStrategy composite = new CompositeSessionAuthenticationStrategy(strategies);
		return composite;
	}

	@Bean(name = "authenticationManager")
	public ProviderManager authenticationManager1(
			@Qualifier("woyaoAuthenticationProvider") AuthenticationProvider authenticationProvider) {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(authenticationProvider);
		ProviderManager bean = new ProviderManager(providers);
		return bean;
	}

	@Bean(name = "csrfRequestMatcher")
	public RequestMatcher csrfRequestMatcher() {
		CsrfRequestMatcher matcher = new CsrfRequestMatcher();
		return matcher;
	}

	@Configuration
	@EnableWebSecurity
	@Order(2)
	class SecurityAdapter extends WebSecurityConfigurerAdapter {

		@Resource(name = "usernamePasswordAuthenticationFilter")
		private Filter usernamePasswordAuthenticationFilter;

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/admin/resources/**", "/resources/**", "/favicon.ico", "/ali/**", "/test/**",
					"/MP_verify_ExuzNoCNVM22thc+.txt**", "/m/**");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// http.csrf().requireCsrfProtectionMatcher(csrfRequestMatcher());
			http.addFilterBefore(concurrentSessionFilter(), ConcurrentSessionFilter.class);
			http.addFilter(this.usernamePasswordAuthenticationFilter);
			http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
			http.csrf().disable();
			http.logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin");
			http.authorizeRequests()
				.antMatchers("/login.jsp**", "/login**").anonymous()
				.antMatchers("/logout**").authenticated()
				// .antMatchers("/**", "/index.jsp**", "/admin/**").hasAnyRole("SUPER", "ADMIN", "OP", "CS")
				.antMatchers("/admin/**").authenticated()
				// .antMatchers("/", "/admin/**").hasAnyAuthority("ROLE_SUPER","ROLE_ADMIN", "ROLE_OP", "ROLE_CS")
				.antMatchers("/api/op2.html**").access("hasRole('OP') and hasRole('ADMIN')")
				// .antMatchers("/admin/**").hasAnyRole("SUPER", "ADMIN", "OP", "CS")
				.anyRequest().denyAll();
		}
	}

}
