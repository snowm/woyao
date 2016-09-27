package com.woyao.admin.dto.purchase;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woyao.admin.dto.BasePKDTO;
import com.woyao.admin.dto.DTOConfig;

public class OrderDTO extends BasePKDTO {

	private static final long serialVersionUID = -4848203589081607445L;

	private String tbOrderNo;

	private Integer totalPrice;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date aliCreationDate;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date creationDate;

	@JsonFormat(pattern = DTOConfig.DATE_TIME_FULL_FMT)
	private Date lastModifiedDate;
}
