package com.woyao.wx;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.snowm.utils.encrypt.MD5Encrypt;
import com.snowm.utils.encrypt.SHA1Encrypt;

public class WxUtils {

	public static final String DT_PATTERN = "yyyyMMddHHmmss";

	public static final DateFormat DF = new SimpleDateFormat(DT_PATTERN);

	private static final String PARAM_EQ_CHAR = "=";

	private static final String PARAM_SEPERATOR = "&";

	private static final Random r = new Random();

	public static String decode(String encoded) {
		String source = null;
		return source;
	}

	public static String encode(String source) {
		String encoded = null;
		return encoded;
	}

	public static String generateNonce(int length) {
		String source = System.currentTimeMillis() + "-" + r.nextInt();
		return MD5Encrypt.encrypt(source.getBytes(), false, length);
	}

	public static String generateTimestamp() {
		return System.currentTimeMillis() / 1000 + "";
	}

	private static Comparator<NameValuePair> comparator = new Comparator<NameValuePair>() {

		@Override
		public int compare(NameValuePair o1, NameValuePair o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};

	public static String generatePaySign(List<NameValuePair> parameters, String privateKey) {
		Collections.sort(parameters, comparator);
		StringBuilder sb = new StringBuilder();
		boolean addSeperator = false;
		for (NameValuePair p : parameters) {
			if (addSeperator) {
				sb.append(PARAM_SEPERATOR);
			} else {
				addSeperator = true;
			}
			sb.append(p.getName()).append(PARAM_EQ_CHAR).append(p.getValue());
		}
		sb.append(PARAM_SEPERATOR).append("key=").append(privateKey);
		try {
			String encoded = MD5Encrypt.encrypt(sb.toString().getBytes("utf-8"), true);
			return encoded;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String generateJsapiSign(List<NameValuePair> parameters) {
		Collections.sort(parameters, comparator);
		StringBuilder sb = new StringBuilder();
		boolean addSeperator = false;
		for (NameValuePair p : parameters) {
			if (addSeperator) {
				sb.append(PARAM_SEPERATOR);
			} else {
				addSeperator = true;
			}
			sb.append(p.getName()).append(PARAM_EQ_CHAR).append(p.getValue());
		}
		try {
			String encoded = SHA1Encrypt.encrypt(sb.toString().getBytes("utf-8"), false);
			return encoded;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static NameValuePair generateNVPair(String name, String value) {
		// String encodedValue = URLEncoder.encode(value, "GBK");
		// NameValuePair nvp = new BasicNameValuePair(name, encodedValue);

		NameValuePair nvp = new BasicNameValuePair(name, value);
		return nvp;
	}

	public static String formatDate(Date date) {
		return DF.format(date);
	}

	public static Date parseDate(String dateStr) {
		try {
			return DF.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
}
