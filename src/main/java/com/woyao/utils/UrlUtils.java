package com.woyao.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.woyao.security.Oauth2SecurityFilter;

public abstract class UrlUtils {

	/**
	 * Calculate the current URI given the request.
	 * 
	 * @param request
	 *            The request.
	 * @return The current uri.
	 */
	public static String calculateCurrentUri(HttpServletRequest request) throws UnsupportedEncodingException {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request);
		// Now work around SPR-10172...
		String queryString = request.getQueryString();
		// nginx反向代理设置的header
		String originProtocol = request.getHeader("Origin-Protocol");
		String originHost = request.getHeader("Origin-Host");
		String originPortStr = request.getHeader("Origin-Port");
		if (!StringUtils.isBlank(originProtocol) && !StringUtils.isBlank(originHost) && !StringUtils.isBlank(originPortStr)) {
			int originPort = Integer.parseInt(originPortStr);
			builder.scheme(originProtocol).host(originHost);
			if (originPort > 0) {
				if (originPort != 80 && originPort != 443) {
					// 不是标准端口，那么设置上去
					builder.port(originPort);
				} else {
					// 否者清掉port，在省城的url中就没有:port
					builder.port(-1);
				}
			}
		}
		boolean legalSpaces = queryString != null && queryString.contains("+");
		if (legalSpaces) {
			builder.replaceQuery(queryString.replace("+", "%20"));
		}
		UriComponents uri = null;
		try {
			uri = builder.replaceQueryParam(Oauth2SecurityFilter.PARA_OAUTH_CODE).build(true);
		} catch (IllegalArgumentException ex) {
			// ignore failures to parse the url (including query string). does't
			// make sense for redirection purposes anyway.
			return null;
		}
		String query = uri.getQuery();
		if (legalSpaces) {
			query = query.replace("%20", "+");
		}
		return ServletUriComponentsBuilder.fromUri(uri.toUri()).replaceQuery(query).build().toString();
	}
}
