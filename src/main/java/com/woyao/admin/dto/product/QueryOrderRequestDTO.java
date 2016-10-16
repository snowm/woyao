package com.woyao.admin.dto.product;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.woyao.PaginationQueryRequestDTO;

public class QueryOrderRequestDTO extends PaginationQueryRequestDTO{

	private static final long serialVersionUID = 1L;
	
	private Integer statusId;//成功或否
	
	private Long mintotalFee;//最小费用
	
	private Long maxtotalFee;//最大费用
	
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	private Date startcreationDate;//开始创建时间

	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date endcreationDate; //最后创建时间
	
	private Boolean deleted = null;

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Long getMintotalFee() {
		return mintotalFee;
	}

	public void setMintotalFee(Long mintotalFee) {
		this.mintotalFee = mintotalFee;
	}

	public Long getMaxtotalFee() {
		return maxtotalFee;
	}

	public void setMaxtotalFee(Long maxtotalFee) {
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
