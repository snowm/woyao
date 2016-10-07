package com.woyao.admin.dto.product;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woyao.PaginationQueryRequestDTO;
import com.woyao.admin.dto.DTOConfig;

public class QueryOrderRequestDTO extends PaginationQueryRequestDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	
	private Integer statusId;//成功或否
	
	private Integer mintotalFee;//最小费用
	
	private Integer maxtotalFee;//最大费用
	
	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date startcreationDate;//开始创建时间

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date endcreationDate; //最后创建时间

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getMintotalFee() {
		return mintotalFee;
	}

	public void setMintotalFee(Integer mintotalFee) {
		this.mintotalFee = mintotalFee;
	}

	public Integer getMaxtotalFee() {
		return maxtotalFee;
	}

	public void setMaxtotalFee(Integer maxtotalFee) {
		this.maxtotalFee = maxtotalFee;
	}

	public Date getStartcreationDate() {
		return startcreationDate;
	}

	public void setStartcreationDate(Date startcreationDate) {
		this.startcreationDate = startcreationDate;
	}

	public Date getEndcreationDate() {
		return endcreationDate;
	}

	public void setEndcreationDate(Date endcreationDate) {
		this.endcreationDate = endcreationDate;
	}
	
	

	
	
}
