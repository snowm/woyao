package com.woyao.wx;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.woyao.JsonUtils;
import com.woyao.utils.JaxbUtils;
import com.woyao.wx.dto.ErrorResponse;
import com.woyao.wx.dto.GetAccessTokenResponse;
import com.woyao.wx.dto.GetGlobalAccessTokenResponse;
import com.woyao.wx.dto.GetUserInfoResponse;
import com.woyao.wx.dto.UnifiedOrderRequest;
import com.woyao.wx.dto.UnifiedOrderResponse;
import com.woyao.wx.third.dto.GetAccessTokenRequest;

public class WxPayEndpoint {

	private static final String QUERY_PARA_OPEN_ID = "openid";
	private static final String QUERY_PARA_GRANT_TYPE = "grant_type";
	private static final String QUERY_PARA_APPID = "appid";
	private static final String QUERY_PARA_ACCESS_TOKEN = "access_token";

	private Log log = LogFactory.getLog(this.getClass());

	private String wxUnifiedOrderUrl;

	private Client client;

	public UnifiedOrderResponse unifiedOrder(String appId, String mchId) {
		WebTarget target = client.target(this.wxUnifiedOrderUrl);
		UnifiedOrderRequest req = new UnifiedOrderRequest();
		String body = null;
		try {
			body = JaxbUtils.marshall(req);
		} catch (JAXBException e1) {
			throw new RuntimeException(e1);
		}
		Entity<String> entity = Entity.entity(body, MediaType.TEXT_PLAIN);
		Response resp = createXmlRequestBuilder(target).post(entity);

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			String responseBody = resp.readEntity(String.class);
			UnifiedOrderResponse orderResponse = JaxbUtils.unmarshall(UnifiedOrderResponse.class, responseBody);
			return orderResponse;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}

	private Builder createXmlRequestBuilder(WebTarget target) {
		return target.request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML_TYPE, MediaType.TEXT_PLAIN_TYPE)
				.header("User-Agent", MediaType.APPLICATION_XML);
	}

	private boolean validateResponse(Response resp) throws RuntimeException {
		if (resp.getStatus() >= Response.Status.BAD_REQUEST.getStatusCode()) {
			try {
				String error = resp.readEntity(String.class);
				log.error("Error response: " + error);
			} catch (Exception e) {
				String msg = "Can not parse error response!";
				log.error(msg, e);
			}
			return false;
		}
		return true;
	}

	public String getWxUnifiedOrderUrl() {
		return wxUnifiedOrderUrl;
	}

	public void setWxUnifiedOrderUrl(String wxUnifiedOrderUrl) {
		this.wxUnifiedOrderUrl = wxUnifiedOrderUrl;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
