package com.woyao.security;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class TestDESCodec {

	private static final String KEY = "#9720X!_2!%Ckid";
	private DESCodec target;
	private String original = UUID.randomUUID().toString();

	@Before
	public void init() throws Exception {
		this.target = new DESCodec(KEY);
	}

	@Test
	public void testCrypt() {
		String encrypted = target.encrypt(original);
		System.out.println(encrypted);
		String decrypted = target.decrypt(encrypted);
		assertEquals(this.original, decrypted);
	}
	
}
