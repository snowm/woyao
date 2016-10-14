package com.woyao.wx;

import javax.annotation.Resource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.woyao.admin.service.IOrderAdminService;
import com.woyao.domain.purchase.Order;
import com.woyao.utils.JaxbUtils;
import com.woyao.wx.dto.UnifiedOrderRequest;
import com.woyao.wx.dto.UnifiedOrderRequestDTO;
import com.woyao.wx.dto.UnifiedOrderResponse;
import com.woyao.wx.service.IWxPayService;

public class WxPayEndpoint {

	private static final String QUERY_PARA_OPEN_ID = "openid";
	private static final String QUERY_PARA_GRANT_TYPE = "grant_type";
	private static final String QUERY_PARA_APPID = "appid";
	private static final String QUERY_PARA_ACCESS_TOKEN = "access_token";

	private Log log = LogFactory.getLog(this.getClass());

	private String wxUnifiedOrderUrl;

	private Client client;
	
	@Resource(name="wxAdminService")
	private IWxPayService wxAdminService;

	public UnifiedOrderResponse unifiedOrder(UnifiedOrderRequestDTO dto) {
		WebTarget target = client.target(this.wxUnifiedOrderUrl);
		UnifiedOrderRequest req = new UnifiedOrderRequest();
		BeanUtils.copyProperties(dto, req);
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
			if(orderResponse!=null&&"SUCCESS".equals(orderResponse.getReturnCode())){
				wxAdminService.savePrePayId(orderResponse, req.getOpenid());
				wxAdminService.svaeOrder(dto, orderResponse);			
			}
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
