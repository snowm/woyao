package com.woyao.customer.chat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class UploadUtils {

	public static final String DT_PATTERN = "yyyyMMdd";
	public static final DateFormat DF = new SimpleDateFormat(DT_PATTERN);

	private static final Log log = LogFactory.getLog(UploadUtils.class);

	private static final String PIC_ROOT_PATH = "src/main/webapp/upload";
	private static File root;

	static {
		root = new File(PIC_ROOT_PATH);
		if (!root.exists()) {
			root.mkdir();
		}
	}

	public static byte[] decodePicString(String base64String) {
		Decoder decoder = Base64.getDecoder();
		byte[] bytes = decoder.decode(base64String);
		return bytes;
	}

	public static String generatePicFileName() {
		return generatePicFileName("jpg");
	}
	
	public static String generatePicFileName(String postfix) {
		String dateDir = DF.format(new Date());
		File d = new File(root, dateDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		return dateDir + "/" + UUID.randomUUID().toString() + "." + postfix;
	}

	public static File generatePicFile(String name) {
		File f = new File(root, name);
		return f;
	}

	private static void storePic(byte[] bytes, File file) throws IOException {
		FileOutputStream write = new FileOutputStream(file);
		try {
			write.write(bytes);
		} finally {
			write.close();
		}
	}

	private static void storePic(InputStream in, File file) throws IOException {
		FileOutputStream write = new FileOutputStream(file);
		try {
			int b = -1;
			while ((b = in.read()) != -1) {
				write.write(b);
			}
		} finally {
			write.close();
		}
	}

	private static void storePic(ByteBuffer in, File file) throws IOException {
		FileOutputStream write = new FileOutputStream(file);
		FileChannel fc = write.getChannel();
		try {
			fc.write(in);
		} finally {
			fc.close();
			write.close();
		}
	}

	public static String savePic(String base64String) throws IOException {
		PicInfo info = generatePicInfo(base64String);
		String path = generatePicFileName(info.postfix);
		File file = generatePicFile(path);
		byte[] bytes = decodePicString(info.content);
		storePic(bytes, file);
		return path;
	}

	public static String savePic(ByteBuffer in) throws IOException {
		String path = generatePicFileName();
		File file = generatePicFile(path);
		storePic(in, file);
		return path;
	}

	public static String savePic(InputStream in) throws IOException {
		String path = generatePicFileName();
		File file = generatePicFile(path);
		storePic(in, file);
		return path;
	}

	private static PicInfo generatePicInfo(String base64String) {
		PicInfo info = new PicInfo();
		info.codeDesc = base64String.substring(0, base64String.indexOf(','));
		info.content = base64String.substring(info.codeDesc.length() + 1);
		info.postfix = info.codeDesc.substring(info.codeDesc.indexOf("image/") + "image/".length(), info.codeDesc.indexOf(";"));

		return info;
	}

	static class PicInfo {
		private String codeDesc;
		private String postfix;
		private String content;
	}
}
