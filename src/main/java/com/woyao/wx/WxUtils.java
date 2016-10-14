package com.woyao.wx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

public class WxUtils {

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

	public static String generateSign(List<NameValuePair> parameters, String privateKey) {
		Collections.sort(parameters, comparator);
		StringBuilder sb = new StringBuilder();
		boolean addSeperator = false;
		for (NameValuePair p : parameters) {
			if (addSeperator) {
				sb.append("&");
			} else {
				addSeperator = true;
			}
			sb.append(p.getName()).append(p.getValue());
		}
		sb.append(privateKey);
		try {
			String encoded = MD5Encrypt.encrypt(sb.toString().getBytes("utf-8"), true);
			return encoded;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static final String DT_PATTERN = "yyyyMMddHHmmss";

	public static final DateFormat DF = new SimpleDateFormat(DT_PATTERN);

	public static NameValuePair generateNVPair(String name, String value) throws UnsupportedEncodingException {
		String encodedValue = URLEncoder.encode(value, "GBK");
		NameValuePair nvp = new BasicNameValuePair(name, encodedValue);
		return nvp;
	}

	public static String formatDate(Date date) {
		return DF.format(date);
	}
	
	public static Date parseDate(String dateStr){
		try {
			return DF.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
}
