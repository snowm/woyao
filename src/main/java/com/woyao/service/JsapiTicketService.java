package com.woyao.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woyao.dao.CommonDao;
import com.woyao.domain.wx.JsapiTicket;

@Service("jsapiTicketService")
public class JsapiTicketService {

	@Resource(name = "commonDao")
	private CommonDao dao;

	@Transactional
	public JsapiTicket getTicket() {
		JsapiTicket token = this.dao.queryUnique("from JsapiTicket order by id desc");
		return token;
	}

	@Transactional
	public void saveOrUpdate(JsapiTicket ticket) {
		this.dao.saveOrUpdate(ticket);
	}

}
