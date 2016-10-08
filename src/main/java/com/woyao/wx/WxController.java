package com.woyao.wx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.encrypt.SHA1Encrypt;
import com.woyao.GlobalConfig;

@Controller
@RequestMapping(value = "/wx")
public class WxController {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@RequestMapping(value = { "/callback/{APPID}" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String msgCallback(@PathVariable("APPID") String appId, @RequestBody String msg) {
		return "success";
	}

	@RequestMapping(value = { "/verifyToken" }, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String verify(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
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
