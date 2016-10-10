package com.woyao.wx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snowm.utils.encrypt.SHA1Encrypt;
import com.woyao.GlobalConfig;
import com.woyao.utils.JaxbUtils;
import com.woyao.wx.dto.TestXMLObj;

@Component
@Path("/")
public class WxService {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private GlobalConfig globalConfig;

	@Path("/verifyToken")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String verify(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,
			@QueryParam("nonce") String nonce, @QueryParam("echostr") String echostr, @Context HttpServletRequest request) {
		try {
			String encoded = this.encode(timestamp, nonce, this.globalConfig.getVerifyToken());
			this.log.debug(encoded);
			if (encoded.equals(signature)) {
				return echostr;
			}
		} catch (Exception ex) {
			this.log.warn("Verify token failure!", ex);
		}
		return null;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/orderNotify")
	public String helloWorld(String body) {
		try {
			TestXMLObj in = JaxbUtils.unmarshall(TestXMLObj.class, body);
			in.setId(in.getId() + "-1");
			in.setName(in.getName() + "-1");
			return JaxbUtils.marshall(in);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String encode(String timestamp, String nonce, String token) {
		List<String> list = new ArrayList<>();
		list.add(timestamp);
		list.add(nonce);
		list.add(token);
		Collections.sort(list);
		String s = list.get(0) + list.get(1) + list.get(2);

		String encoded = SHA1Encrypt.encrypt(s.getBytes());
		return encoded;
	}
}
