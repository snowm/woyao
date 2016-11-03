package com.woyao.domain.purchase;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.snowm.hibernate.ext.usertype.ExtEnumType;
import com.woyao.domain.profile.ProfileWX;

@Entity
@Table(name = "PURCHASE_ORDER")
@TableGenerator(name = "orderGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "order", allocationSize = 1, initialValue = 0)
public class Order extends DefaultModelImpl {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderGenerator")
	private Long id;

	@Version
	@Column(name = "VERSION")
	private int version = 0;

	@Column(name = "ORDER_NO", length = 32, nullable = false, unique = true)
	private String orderNo;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "CONSUMER_ID", nullable = false)
	private ProfileWX consumer;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "TO_PROFILE_WX_ID", nullable = true)
	private ProfileWX toProfile;

	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(referencedColumnName = "id", name = "PREPAY_INFO_ID", nullable = true)
	private OrderPrepayInfo prepayInfo;

	@OneToOne(optional = true, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(referencedColumnName = "id", name = "RESULT_INFO_ID", nullable = true)
	private OrderResultInfo resultInfo;

	@Column(name = "ORDER_STATUS")
	@Type(type = ExtEnumType.CLASS_NAME, parameters = { @Parameter(name = ExtEnumType.PARA_ENUMCLASS, value = OrderStatus.CLASS_NAME) })
	private OrderStatus status;

	@Column(name = "TOTAL_FEE", nullable = false)
	private int totalFee;

	@Column(name = "SPBILL_CREATE_IP", nullable = false)
	private String spbillCreateIp;

	@Column(name = "MSG_ID")
	private Long msgId;

	@Column(name = "SHOP_ID")
	private Long shopId;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public ProfileWX getConsumer() {
		return consumer;
	}

	public void setConsumer(ProfileWX consumer) {
		this.consumer = consumer;
	}

	public ProfileWX getToProfile() {
		return toProfile;
	}

	public void setToProfile(ProfileWX toProfile) {
		this.toProfile = toProfile;
	}

	public OrderPrepayInfo getPrepayInfo() {
		return prepayInfo;
	}

	public void setPrepayInfo(OrderPrepayInfo prepayInfo) {
		this.prepayInfo = prepayInfo;
	}

	public OrderResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(OrderResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}
