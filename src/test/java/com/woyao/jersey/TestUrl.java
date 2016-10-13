package com.woyao.jersey;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class TestUrl {

	@Test
	public void testURL(){
		String originUrl = "http://www.luoke30.com/m?x=12&3&code=4";
		try {
			URL url = new URL(originUrl);
			System.out.println(url.getProtocol());
			System.out.println(url.getPort());
			System.out.println(url.getHost());
			System.out.println(url.getPath());
			System.out.println(url.getQuery());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRegex() {
		String originUrl = "http://www.luoke30.com/m?x=12&3&code=44&djier";
		try {
			URL url = new URL(originUrl);
			System.out.println(url.getProtocol());
			System.out.println(url.getPort());
			System.out.println(url.getHost());
			System.out.println(url.getPath());
			System.out.println(url.getQuery());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
