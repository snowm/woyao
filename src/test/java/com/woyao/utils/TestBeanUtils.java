package com.woyao.utils;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

public class TestBeanUtils {

	private static class SourceC {
		private String id;

		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}

	private static class TargetC {
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}
	
	@Test
	public void testCopyMoreFields(){
		SourceC s = new SourceC();
		s.id = "id";
		s.name = "name";
		TargetC t = new TargetC();
		BeanUtils.copyProperties(s, t);
		System.out.println(t.id);
	}
}
