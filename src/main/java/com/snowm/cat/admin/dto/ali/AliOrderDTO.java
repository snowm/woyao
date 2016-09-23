package com.snowm.cat.admin.dto.ali;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.snowm.cat.admin.dto.BasePKDTO;
import com.snowm.cat.admin.dto.DTOConfig;

public class AliOrderDTO extends BasePKDTO {

	private static final long serialVersionUID = -4848203589081607445L;

	private String tbOrderNo;

	private Integer totalPrice;

	/**
	 * 运费
	 */
	private Integer freight;

	private String deliveryNO;

	private AliOrderStatusDTO status;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date aliCreationDate;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationDate;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date lastModifiedDate;

	public String getTbOrderNo() {
		return tbOrderNo;
	}

	public void setTbOrderNo(String tbOrderNo) {
		this.tbOrderNo = tbOrderNo;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDeliveryNO() {
		return deliveryNO;
	}

	public void setDeliveryNO(String deliveryNO) {
		this.deliveryNO = deliveryNO;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	public AliOrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(AliOrderStatusDTO status) {
		this.status = status;
	}

	public Date getAliCreationDate() {
		return aliCreationDate;
	}

	public void setAliCreationDate(Date aliCreationDate) {
		this.aliCreationDate = aliCreationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
