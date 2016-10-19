package com.woyao.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snowm.security.web.authentication.SSLAuthenticationSuccessHandler;

public class SelfSSLAuthenticationSuccessHandler extends SSLAuthenticationSuccessHandler {

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		String targetUrl = super.determineTargetUrl(request, response);
		boolean isShopAdmin = SelfSecurityUtils.hasAuthority(AuthorityConstants.SHOP_ADMIN);
		boolean isAdmin = SelfSecurityUtils.hasAuthority(AuthorityConstants.ADMIN)
				|| SelfSecurityUtils.hasAuthority(AuthorityConstants.SUPER);
		if (isShopAdmin && !isAdmin) {
			targetUrl = "/shopAdmin";
		}

		return targetUrl;
	}

}
