package com.woyao.wx.third;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//@Controller
//@RequestMapping(value = "/wx")
public class WxThirdController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	/**
	 * Accept component_verify_ticket message
	 * 
	 * <pre>
	 * <xml>
	 * 	<AppId> </AppId>
	 * 	<CreateTime>1413192605 </CreateTime>
	 * 	<InfoType> </InfoType>
	 * 	<ComponentVerifyTicket> </ComponentVerifyTicket>
	 * </xml>
	 * </pre>
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/component_verify_ticket" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String verifyTicket(String encoded) {
		return "success";
	}

	@RequestMapping(value = { "/callback/{APPID}" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String msgCallback(@PathVariable("APPID") String appId, @RequestBody String msg) {
		return "success";
	}


}
