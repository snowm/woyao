package com.woyao.domain.purchase;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;

@Entity
@Table(name = "ORDER_RESULT_INFO")
@TableGenerator(name = "orderResultInfoGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "orderResultInfo", allocationSize = 1, initialValue = 0)
public class OrderResultInfo extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderResultInfoGenerator")
	private Long id;

	@Version
	@Column(name = "VERSION")
	private int version;

	@Column(name = "OPEN_ID", length = 50, nullable = false)
	private String openId;

	@Column(name = "RETURN_CODE", length = 10, nullable = false)
	private String returnCode;

	@Column(name = "DESC", length = 200)
	private String desc;

	@Column(name = "RESULT_CODE", length = 20)
	private String resultCode;

	@Column(name = "ERR_CODE", length = 32)
	private String errCode;

	@Column(name = "ERR_CODE_DESC", length = 200)
	private String errCodeDes;

	@Column(name = "TRANSACTION_ID", length = 32)
	private String transactionId;

	@Column(name = "TIME_END")
	private Date timeEnd;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

}
