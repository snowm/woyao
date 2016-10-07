package com.woyao.customer.chat;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.snowm.security.web.json.JsonResultBaseObject;
import com.woyao.admin.dto.product.PicDTO;
import com.woyao.admin.service.IPicAdminService;
import com.woyao.customer.service.IChatService;

@Controller
@RequestMapping("/m/send")
public class ChatController {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "picAdminService")
	private IPicAdminService service;
	
	@Resource(name="chatService")
	private IChatService chatService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JsonResultBaseObject<String> upload(String msg, @RequestParam(value = "pic") MultipartFile upFile, HttpServletRequest request) {
		JsonResultBaseObject<String> result = JsonResultBaseObject.getInstance();
		this.log.info(msg);
		if (upFile == null) {
			result.setErrMsg("Upload file can not be null!");
			return result;
		}
		if (upFile.isEmpty()) {
			result.setErrMsg("Upload file can not be empty!");
			return result;
		} else {
			String realPath = request.getSession().getServletContext().getRealPath("/upload");
			try {
				String url = ChatUtils.savePic(upFile.getInputStream());
				result.setInfoMsg("Upload successfully!");
				PicDTO dto = new PicDTO();
				dto.setPath(realPath);
				dto.setUrl("/pic/" + url);
				dto.setCreationDate(new Date());
				dto.setDeleted(false);
				dto.setEnabled(true);
				dto.setLastModifiedDate(new Date());
				this.service.update(dto);
				result.setResult("1");
			} catch (IOException e) {
				result.setErrMsg("Save file can not be empty!");
				return result;
			}
		}
		return result;
	}

}
