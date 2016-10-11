package com.woyao.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
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
import com.woyao.admin.dto.product.PicDTO;
import com.woyao.admin.service.IPicAdminService;
import com.woyao.customer.chat.UploadUtils;

@Controller
@RequestMapping("/admin/upload")
public class UploadController {

	private Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "picAdminService")
	private IPicAdminService service;

	@RequestMapping(value = "/file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JsonResultBaseObject<PicDTO> upload(String msg, @RequestParam(value = "uploadFile") MultipartFile upFile,
			HttpServletRequest request) {
		JsonResultBaseObject<PicDTO> result = new JsonResultBaseObject<>();
		this.log.info(msg);
		if (upFile == null) {
			result.setErrMsg("Upload file can not be null!");
			return result;
		}
		if (upFile.isEmpty()) {
			result.setErrMsg("Upload file can not be empty!");
			return result;
		} else {
			String realPath = request.getSession().getServletContext().getRealPath("/upload/admin");
			String fileName = this.generateFileName(realPath, upFile.getOriginalFilename());
			try {
				FileUtils.copyInputStreamToFile(upFile.getInputStream(), new File(realPath, fileName));
			} catch (IOException e) {
				result.setErrMsg("Save file can not be empty!");
				return result;
			}
			result.setInfoMsg("Upload successfully!");
			PicDTO dto = new PicDTO();
			dto.setPath(realPath);
			dto.setUrl("/pic/admin/" + fileName);
			dto.setCreationDate(new Date());
			dto.setDeleted(false);
			dto.setEnabled(true);
			dto.setLastModifiedDate(new Date());
			dto = this.service.update(dto);
			result.setResult(dto);
		}
		return result;
	}

	private String generateFileName(String rootPath, String originalFilename) {
		String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
		return this.generatePicFileName(rootPath, postfix);
	}

	private String generatePicFileName(String rootPath, String postfix) {
		String dateDir = UploadUtils.DF.format(new Date());
		File d = new File(rootPath, dateDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		return dateDir + "/" + UUID.randomUUID().toString() + postfix;
	}

}
