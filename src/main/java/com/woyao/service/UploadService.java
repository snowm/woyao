package com.woyao.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class UploadService implements InitializingBean {

	public static final String DT_PATTERN = "yyyyMMdd";

	public static final DateFormat DF = new SimpleDateFormat(DT_PATTERN);

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String uploadRootPath = "src/main/webapp/upload";

	private File root;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Upload root path: {}", this.uploadRootPath);
		root = new File(this.uploadRootPath);
		if (!root.exists()) {
			root.mkdir();
		}
	}

	private byte[] decodePicString(String base64String) {
		Decoder decoder = Base64.getDecoder();
		byte[] bytes = decoder.decode(base64String);
		return bytes;
	}

	private FileInfo generateFileInfo(String postfix, String subRoot) {
		String dateDir = DF.format(new Date());
		File d = root;
		String fileName = UUID.randomUUID().toString() + "." + postfix;
		String path = dateDir + File.separatorChar + fileName;
		if (!StringUtils.isBlank(subRoot)) {
			d = new File(d, subRoot);
			path = subRoot + File.separatorChar + path;
		}
		d = new File(d, dateDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		FileInfo info = new FileInfo();
		info.file = new File(root, path);
		info.path = path;
		info.fileName = fileName;
		info.url = this.getUrl(path);
		return info;
	}

	public FileInfo savePic(String base64String) throws IOException {
		return this.savePic(base64String, null);
	}

	public FileInfo savePic(String base64String, String subRoot) throws IOException {
		PicInfo picInfo = generatePicInfo(base64String);
		FileInfo fileInfo = generateFileInfo(picInfo.postfix, subRoot);
		byte[] bytes = decodePicString(picInfo.content);
		FileUtils.writeByteArrayToFile(fileInfo.file, bytes);
		logger.debug("saved file: {}", fileInfo.file.getAbsolutePath());
		return fileInfo;
	}

	public FileInfo savePic(InputStream source, String originalFileName, String subRoot) throws IOException {
		String postfix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
		FileInfo fileInfo = generateFileInfo(postfix, subRoot);
		FileUtils.copyInputStreamToFile(source, fileInfo.file);
		logger.debug("saved file: {}" + fileInfo.file.getAbsolutePath());
		return fileInfo;
	}

	private String getUrl(String path) {
		return "/pic/" + path;
	}

	private PicInfo generatePicInfo(String base64String) {
		PicInfo info = new PicInfo();
		info.codeDesc = base64String.substring(0, base64String.indexOf(','));
		info.content = base64String.substring(info.codeDesc.length() + 1);
		info.postfix = info.codeDesc.substring(info.codeDesc.indexOf("image/") + "image/".length(), info.codeDesc.indexOf(";"));

		return info;
	}

	public void setUploadRootPath(String uploadRootPath) {
		this.uploadRootPath = uploadRootPath;
	}

	class PicInfo {
		private String codeDesc;
		private String postfix;
		private String content;
	}

	public class FileInfo {
		private File file;
		private String fileName;
		private String path;
		private String url;

		public String getPath() {
			return path;
		}

		public String getFileName() {
			return fileName;
		}

		public File getFile() {
			return file;
		}

		public String getUrl() {
			return url;
		}

	}

}
