package com.snowm.cat.admin.controller;

import java.lang.reflect.ParameterizedType;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snowm.cat.admin.dto.BasePKDTO;
import com.snowm.cat.admin.service.IAdminService;
import com.snowm.security.web.exception.NotFoundException;

public abstract class AbstractBaseController<M, DTO extends BasePKDTO> {

	protected IAdminService<M, DTO> baseService;
	private Class<M> clazz;

	public abstract void setBaseService(IAdminService<M, DTO> baseService);

	@SuppressWarnings("unchecked")
	public AbstractBaseController() {
		this.clazz = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@RequestMapping(value = { "/enabled/{id}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public DTO enable(@PathVariable("id") long id) {
		DTO dto = this.baseService.enable(id);
		if (dto == null) {
			throw new NotFoundException(String.format("%1$s with id : %2$s cound not found!", this.clazz.getName(), id));
		}
		return dto;
	}
	
	@RequestMapping(value = { "/disable/{id}" }, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public DTO disable(@PathVariable("id") long id) {
		DTO dto = this.baseService.disable(id);
		if (dto == null) {
			throw new NotFoundException(String.format("%1$s with id : %2$s cound not found!", this.clazz.getName(), id));
		}
		return dto;
	}

	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public DTO get(@PathVariable("id") long id) {
		DTO dto = this.baseService.get(id);
		if (dto == null) {
			throw new NotFoundException(String.format("%1$s with id : %2$s cound not found!", this.clazz.getName(), id));
		}
		return dto;
	}

}
