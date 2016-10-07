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

public abstract class ChatUtils {

	private static final Log log = LogFactory.getLog(ChatUtils.class);

	private static final String DT_PATTERN = "yyyyMMdd";
	private static final DateFormat DF = new SimpleDateFormat(DT_PATTERN);
	private static final String PIC_ROOT_PATH = "src/main/webapp" + File.separator + "upload";
	private static File root;

	static {
		root = new File(PIC_ROOT_PATH);
		if (!root.exists()) {
			root.mkdir();
		}
		// } catch (URISyntaxException e) {
		// e.printStackTrace();
		// }
	}

	public static byte[] decodePicString(String base64String) {
		Decoder decoder = Base64.getDecoder();
		String str = base64String.substring(23);
		byte[] bytes = decoder.decode(str);
		return bytes;
	}

	public static String generatePicFileName() {
		String dateDir = DF.format(new Date());
		File d = new File(root, dateDir);
		if (!d.exists()) {
			d.mkdirs();
		}
		return dateDir + "/" + UUID.randomUUID().toString() + ".jpg";
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
		String path = generatePicFileName();
		File file = generatePicFile(path);
		System.out.println(file.getAbsolutePath());
		byte[] bytes = decodePicString(base64String);
		storePic(bytes, file);
		return path;
	}

	public static String savePic(ByteBuffer in) throws IOException {
		String path = generatePicFileName();
		File file = generatePicFile(path);
		System.out.println(file.getAbsolutePath());
		storePic(in, file);
		return path;
	}

	public static String savePic(InputStream in) throws IOException {
		String path = generatePicFileName();
		File file = generatePicFile(path);
		System.out.println(file.getAbsolutePath());
		storePic(in, file);
		return path;
	}
}
