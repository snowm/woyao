package com.woyao.wx;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.woyao.wx.dto.ErrorResponse;
import com.woyao.wx.dto.GetAccessTokenResponse;
import com.woyao.wx.dto.GetGlobalAccessTokenResponse;
import com.woyao.wx.dto.GetUserInfoResponse;

public class WxEndpoint {

	private static final String QUERY_PARA_OPEN_ID = "openid";
	private static final String QUERY_PARA_GRANT_TYPE = "grant_type";
	private static final String QUERY_PARA_APPID = "appid";
	private static final String QUERY_PARA_ACCESS_TOKEN = "access_token";

	private Log log = LogFactory.getLog(this.getClass());

	private String getGlobalAccessTokenUrl;

	private String getAccessTokenUrl;

	private String refreshAccessTokenUrl;

	private String getUserInfoUrl;

	private Client client;

	/**
	 * <pre>
	 * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
	 * 开发者需要进行妥善保存。access_token的存储至少要保留512个字符空间。
	 * access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
	 * 
	 * 1、为了保密appsecrect，第三方需要一个access_token获取和刷新的中控服务器。
	 * 而其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，
	 * 否则会造成access_token覆盖而影响业务；
	 * 
	 * 2、目前access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。
	 * 中控服务器需要根据这个有效时间提前去刷新新access_token。
	 * 在刷新过程中，中控服务器对外输出的依然是老access_token，此时公众平台后台会保证在刷新短时间内，
	 * 新老access_token都可用，这保证了第三方业务的平滑过渡；
	 * 
	 * 3、access_token的有效时间可能会在未来有调整，所以中控服务器不仅需要内部定时主动刷新，
	 * 还需要提供被动刷新access_token的接口，这样便于业务服务器在API调用获知access_token已超时的情况下，
	 * 可以触发access_token的刷新流程。
	 * 
	 * 公众号可以使用AppID和AppSecret调用本接口来获取access_token。
	 * AppID和AppSecret可在微信公众平台官网-开发页中获得（需要已经成为开发者，且帐号没有异常状态）。
	 * 注意调用所有微信接口时均需使用https协议。如果第三方不使用中控服务器，
	 * 而是选择各个业务逻辑点各自去刷新access_token，那么就可能会产生冲突，导致服务不稳定。
	 * 
	 * </pre>
	 * 
	 * @param appId
	 * @param appSecret
	 * @param grantType
	 *            应该固定传入client_credential
	 * @return token；如果获取失败，返回null
	 */
	public GetGlobalAccessTokenResponse getGlobalAccessToken(String appId, String appSecret, String grantType) {
		WebTarget target = client.target(this.getGlobalAccessTokenUrl).queryParam(QUERY_PARA_GRANT_TYPE, grantType)
				.queryParam(QUERY_PARA_APPID, appId).queryParam("secret", appSecret);

		Response resp = createRequestBuilder(target).get();

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			GetGlobalAccessTokenResponse result = resp.readEntity(GetGlobalAccessTokenResponse.class);
			return result;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}

	/**
	 * <pre>
	 * 通过code换取网页授权access_token
	 * 
	 * 首先请注意，这里通过code换取的是一个特殊的网页授权access_token, 
	 * 与基础支持中的access_token（该access_token用于调用其他接口）不同。
	 * 
	 * 公众号可通过下述接口来获取网页授权access_token。
	 * 如果网页授权的作用域为snsapi_base，则本步骤中获取到网页授权access_token的同时，
	 * 也获取到了openid，snsapi_base式的网页授权流程即到此为止。
	 * 
	 * 尤其注意：由于公众号的secret和获取到的access_token安全级别都非常高，必须只保存在服务器，
	 * 不允许传给客户端。后续刷新access_token、通过access_token获取用户信息等步骤，也必须从服务器发起。
	 * </pre>
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @param grantType
	 *            填写为authorization_code
	 * @return
	 */
	public GetAccessTokenResponse getAccessToken(String appId, String appSecret, String code, String grantType) {
		WebTarget target = client.target(this.getAccessTokenUrl).queryParam(QUERY_PARA_APPID, appId).queryParam("secret", appSecret)
				.queryParam("code", code).queryParam(QUERY_PARA_GRANT_TYPE, grantType);

		Response resp = createRequestBuilder(target).get();

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
	 * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
	 * refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
	 * </pre>
	 * 
	 * @param appId
	 * @param refreshToken
	 * @param grantType
	 *            填写为refresh_token
	 * @return
	 */
	public GetAccessTokenResponse refreshAccessToken(String appId, String refreshToken, String grantType) {
		WebTarget target = client.target(this.refreshAccessTokenUrl).queryParam(QUERY_PARA_APPID, appId)
				.queryParam(QUERY_PARA_GRANT_TYPE, grantType).queryParam("refresh_token", refreshToken);

		Response resp = createRequestBuilder(target).get();

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
	 * 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
	 * </pre>
	 * 
	 * @param accessToken
	 * @param openId
	 * @param lang
	 * @return
	 */
	public GetUserInfoResponse getUserInfo(String accessToken, String openId, String lang) {
		WebTarget target = client.target(this.getUserInfoUrl).queryParam(QUERY_PARA_ACCESS_TOKEN, accessToken)
				.queryParam(QUERY_PARA_OPEN_ID, openId).queryParam("lang", lang);

		Response resp = createRequestBuilder(target).get();

		if (!validateResponse(resp)) {
			return null;
		}

		try {
			GetUserInfoResponse result = resp.readEntity(GetUserInfoResponse.class);
			return result;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return null;
	}

	/**
	 * <pre>
	 * 检验授权凭证（access_token）是否有效
	 * </pre>
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public boolean verifyAccessToken(String accessToken, String openId) {
		WebTarget target = client.target(this.getUserInfoUrl).queryParam(QUERY_PARA_ACCESS_TOKEN, accessToken)
				.queryParam(QUERY_PARA_OPEN_ID, openId);

		Response resp = createRequestBuilder(target).get();

		if (!validateResponse(resp)) {
			return false;
		}

		try {
			ErrorResponse result = resp.readEntity(ErrorResponse.class);
			if (result.getErrCode() != null) {
				return result.getErrCode().equals("0");
			}
			return false;
		} catch (Exception e) {
			String msg = "Can not parse response!";
			log.error(msg, e);
		}
		return false;
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

	public void setGetGlobalAccessTokenUrl(String getGlobalAccessTokenUrl) {
		this.getGlobalAccessTokenUrl = getGlobalAccessTokenUrl;
	}

	public void setGetAccessTokenUrl(String getAccessTokenUrl) {
		this.getAccessTokenUrl = getAccessTokenUrl;
	}

	public void setRefreshAccessTokenUrl(String refreshAccessTokenUrl) {
		this.refreshAccessTokenUrl = refreshAccessTokenUrl;
	}

	public void setGetUserInfoUrl(String getUserInfoUrl) {
		this.getUserInfoUrl = getUserInfoUrl;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
