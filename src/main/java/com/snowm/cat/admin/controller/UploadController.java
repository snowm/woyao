package com.snowm.cat.admin.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
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

@Controller
@RequestMapping("/upload")
public class UploadController {

	private Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "x1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JsonResultBaseObject<String> upload(String msg, @RequestParam(value = "uploadFile") MultipartFile upFile,
			HttpServletRequest request) {
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
			String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
			try {
				FileUtils.copyInputStreamToFile(upFile.getInputStream(),
						new File(realPath, upFile.getOriginalFilename()));
			} catch (IOException e) {
				result.setErrMsg("Save file can not be empty!");
				return result;
			}
			result.setInfoMsg("Upload successfully!");
			result.setResult("1");
		}
		return result;
	}

}
