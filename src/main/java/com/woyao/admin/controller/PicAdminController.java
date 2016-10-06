package com.woyao.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.utils.query.PaginationBean;
import com.woyao.admin.dto.product.PicDTO;
import com.woyao.admin.dto.product.QueryPicRequestDTO;
import com.woyao.admin.service.IAdminService;
import com.woyao.admin.service.IPicAdminService;
import com.woyao.domain.Pic;

@Controller
@RequestMapping(value = "/admin/pic")
public class PicAdminController extends AbstractBaseController<Pic, PicDTO> {

	@Resource(name = "picAdminService")
	private IPicAdminService service;

	@RequestMapping(value = { "/search" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PaginationBean<PicDTO> query(QueryPicRequestDTO request) {
		PaginationBean<PicDTO> result = this.service.query(request);
		return result;
	}

	@RequestMapping(value = { "", "/" }, method = { RequestMethod.PUT,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PicDTO saveOrUpdate(@RequestBody PicDTO dto) {
		if (dto.getId() != null) {
			return this.service.update(dto);
		} else {
			return this.service.create(dto);
		}
	}
	
	@Resource
	@Override
	public void setBaseService(@Qualifier("picAdminService") IAdminService<Pic, PicDTO> baseService) {
		this.baseService = baseService;
	}

}
