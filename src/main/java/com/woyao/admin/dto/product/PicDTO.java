package com.woyao.admin.dto.product;

import com.woyao.admin.dto.BasePKDTO;

public class PicDTO extends BasePKDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String path;
	
	private String url;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
