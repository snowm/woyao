package com.woyao.wx.third;

import javax.annotation.Resource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.woyao.wx.third.dto.AuthorizeRequest;
import com.woyao.wx.third.dto.AuthorizeResponse;
import com.woyao.wx.third.dto.GetAccessTokenRequest;
import com.woyao.wx.third.dto.GetAccessTokenResponse;
import com.woyao.wx.third.dto.GetAuthorizerInfoRequest;
import com.woyao.wx.third.dto.GetAuthorizerInfoResponse;
import com.woyao.wx.third.dto.GetAuthorizerTokenRequest;
import com.woyao.wx.third.dto.GetAuthorizerTokenResponse;
import com.woyao.wx.third.dto.GetPreAuthCodeRequest;
import com.woyao.wx.third.dto.GetPreAuthCodeResponse;

//@Component("wxThirdEndpoint")
//@PropertySource("classpath:/wx.properties")
public class WxThirdEndpoint {

	private Log log = LogFactory.getLog(this.getClass());

	private final static String QUERY_PARA_ACCESS_TOKEN = "component_access_token";

	@Value("${wx.3rd.api.getAccessToken.url}")
	private String getAccessTokenUrl;

	@Value("${wx.3rd.api.getPreAuthCode.url}")
	private String getPreAuthCodeUrl;

	@Value("${wx.3rd.api.authorize.url}")
	private String authorizeUrl;

	@Value("${wx.3rd.api.getAuthorizerToken.url}")
	private String getAuthorizerTokenUrl;

	@Value("${wx.3rd.api.getAuthorizerInfo.url}")
	private String getAuthorizerInfoUrl;

	@Resource(name = "wxJerseyClient")
	private Client client;

	/**
	 * <pre>
	 * 第三方平台compoment_access_token是第三方平台的下文中接口的调用凭据，也叫做令牌（component_access_token）。
	 * 每个令牌是存在有效期（2小时）的，且令牌的调用不是无限制的，请第三方平台做好令牌的管理，
	 * 在令牌快过期时（比如1小时50分）再进行刷新。
	 * </pre>
	 * 
	 * @param appId
	 * @param appSecret
	 * @param verifyTicket
	 * @return token；如果获取失败，返回null
	 */
	public GetAccessTokenResponse getAccessToken(String appId, String appSecret, String verifyTicket) {
		WebTarget target = client.target(this.getAccessTokenUrl);
		GetAccessTokenRequest req = new GetAccessTokenRequest(appId, appSecret, verifyTicket);

		Entity<GetAccessTokenRequest> entity = Entity.entity(req, MediaType.APPLICATION_JSON_TYPE);
		Response resp = createRequestBuilder(target).post(entity);

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			GetAccessTokenResponse result = resp.readEntity(GetAccessTokenResponse.class);
			return result;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}

	/**
	 * <pre>
	 * 该API用于获取预授权码。预授权码用于公众号授权时的第三方平台方安全验证。
	 * 一般有效时间10分钟
	 * </pre>
	 * 
	 * @param appId
	 * @param accessToken
	 * @return code；如果获取失败，返回null
	 */
	public GetPreAuthCodeResponse getPreAuthCode(String appId, String accessToken) {
		WebTarget target = client.target(this.getPreAuthCodeUrl).queryParam(QUERY_PARA_ACCESS_TOKEN, accessToken);
		GetPreAuthCodeRequest req = new GetPreAuthCodeRequest(appId);

		Entity<GetPreAuthCodeRequest> entity = Entity.entity(req, MediaType.APPLICATION_JSON_TYPE);
		Response resp = createRequestBuilder(target).post(entity);

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			GetPreAuthCodeResponse result = resp.readEntity(GetPreAuthCodeResponse.class);
			return result;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}

	/**
	 * <pre>
	 * 该API用于使用授权码换取授权公众号的授权信息，并换取authorizer_access_token和authorizer_refresh_token。
	 * 授权码的获取，需要在用户在第三方平台授权页中完成授权流程后，在回调URI中通过URL参数提供给第三方平台方。
	 * 请注意，由于现在公众号可以自定义选择部分权限授权给第三方平台，因此，
	 * 第三方平台开发者需要通过该接口来获取公众号具体授权了哪些权限，
	 * 而不是简单地认为自己声明的权限就是公众号授权的权限。
	 * </pre>
	 * 
	 * @param appId
	 * @param accessToken
	 * @param authorizationCode
	 * @return
	 */
	public AuthorizeResponse authorize(String appId, String accessToken, String authorizationCode) {
		WebTarget target = client.target(this.authorizeUrl).queryParam(QUERY_PARA_ACCESS_TOKEN, accessToken);
		AuthorizeRequest req = new AuthorizeRequest(appId, authorizationCode);

		Entity<AuthorizeRequest> entity = Entity.entity(req, MediaType.APPLICATION_JSON_TYPE);
		Response resp = createRequestBuilder(target).post(entity);

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			AuthorizeResponse result = resp.readEntity(AuthorizeResponse.class);
			return result;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}

	/**
	 * <pre>
	 * 该API用于在授权方令牌（authorizer_access_token）失效时，可用刷新令牌（authorizer_refresh_token）
	 * 获取新的令牌。请注意，此处token是2小时刷新一次，开发者需要自行进行token的缓存，
	 * 避免token的获取次数达到每日的限定额度。
	 * 缓存方法可以参考：http://mp.weixin.qq.com/wiki/2/88b2bf1265a707c031e51f26ca5e6512.html
	 * </pre>
	 * 
	 * @param appId
	 * @param accessToken
	 * @param authorizerAppid
	 * @param authorizerRefreshToken
	 * @return
	 */
	public GetAuthorizerTokenResponse getAuthorizerToken(String appId, String accessToken, String authorizerAppid, String authorizerRefreshToken) {
		WebTarget target = client.target(this.getAuthorizerTokenUrl).queryParam(QUERY_PARA_ACCESS_TOKEN, accessToken);
		GetAuthorizerTokenRequest req = new GetAuthorizerTokenRequest(appId, authorizerAppid, authorizerRefreshToken);

		Entity<GetAuthorizerTokenRequest> entity = Entity.entity(req, MediaType.APPLICATION_JSON_TYPE);
		Response resp = createRequestBuilder(target).post(entity);

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			GetAuthorizerTokenResponse result = resp.readEntity(GetAuthorizerTokenResponse.class);
			return result;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}
	

	/**
	 * <pre>
	 * 该API用于获取授权方的公众号基本信息，包括头像、昵称、帐号类型、认证类型、微信号、原始ID和二维码图片URL。
	 * 需要特别记录授权方的帐号类型，在消息及事件推送时，对于不具备客服接口的公众号，需要在5秒内立即响应；
	 * 而若有客服接口，则可以选择暂时不响应，而选择后续通过客服接口来发送消息触达粉丝。
	 * </pre>
	 * @param appId
	 * @param accessToken
	 * @param authorizerAppid
	 * @return
	 */
	public GetAuthorizerInfoResponse getAuthorizerInfo(String appId, String accessToken, String authorizerAppid) {
		WebTarget target = client.target(this.getAuthorizerInfoUrl).queryParam(QUERY_PARA_ACCESS_TOKEN, accessToken);
		GetAuthorizerInfoRequest req = new GetAuthorizerInfoRequest(appId, authorizerAppid);

		Entity<GetAuthorizerInfoRequest> entity = Entity.entity(req, MediaType.APPLICATION_JSON_TYPE);
		Response resp = createRequestBuilder(target).post(entity);

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			GetAuthorizerInfoResponse result = resp.readEntity(GetAuthorizerInfoResponse.class);
			return result;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}

	private Builder createRequestBuilder(WebTarget target) {
		return target.request(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).header("User-Agent",
				MediaType.APPLICATION_JSON);
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
}
