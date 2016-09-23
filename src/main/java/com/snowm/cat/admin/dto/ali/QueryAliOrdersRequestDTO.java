package com.snowm.cat.admin.dto.ali;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.snowm.cat.admin.dto.DTOConfig;
import com.snowm.cat.admin.dto.PaginationQueryRequestDTO;

public class QueryAliOrdersRequestDTO extends PaginationQueryRequestDTO {

	private static final long serialVersionUID = 6065375426171420402L;

	private String tbOrderNo;

	private String customerAccount;

	private AliOrderStatusDTO orderStatus;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationFromDate;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationToDate;

	private Boolean deleted = false;

	public String getTbOrderNo() {
		return tbOrderNo;
	}

	public void setTbOrderNo(String tbOrderNo) {
		this.tbOrderNo = tbOrderNo;
	}

	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public AliOrderStatusDTO getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(AliOrderStatusDTO orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreationFromDate() {
		return creationFromDate;
	}

	public void setCreationFromDate(Date creationFromDate) {
		this.creationFromDate = creationFromDate;
	}

	public Date getCreationToDate() {
		return creationToDate;
	}

	public void setCreationToDate(Date creationToDate) {
		this.creationToDate = creationToDate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}
