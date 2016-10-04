package com.woyao.customer.chat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ChatUtils {

	private static final Log log = LogFactory.getLog(ChatUtils.class);

	private static final String DT_PATTERN = "yyyyMMdd";
	private static final DateFormat DF = new SimpleDateFormat(DT_PATTERN);
	private static final String PIC_ROOT_PATH = "\\upload";

	static {
		URL url = ChatUtils.class.getClassLoader().getResource(PIC_ROOT_PATH);
		try {
			File root = new File(url.toURI());
			if (!root.exists()) {
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static byte[] decodePicString(String base64String) {
		Decoder decoder = Base64.getDecoder();
		byte[] bytes = decoder.decode(base64String);
		return bytes;
	}

	public static String generatePicName() {
		String dateDir = DF.format(new Date());
		return dateDir + "\\" + UUID.randomUUID().toString();
	}

	public static void storePic(byte[] bytes, String name) throws IOException {
		FileOutputStream write = new FileOutputStream("\\upload\\" + name);
		try {
			write.write(bytes);
		} finally {
			write.close();
		}
	}

	public static String savePic(byte[] bytes) throws IOException {
		String name = generatePicName();
		storePic(bytes, name);
		return name;
	}
}
