package com.woyao.domain.ali;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.snowm.hibernate.ext.domain.Modification;
import com.snowm.hibernate.ext.domain.ModificationModel;

@Entity
@Table(name = "TIME_LIMIT_CONFIG")
@TableGenerator(name = "timeLimitConfigGenerator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VALUE", pkColumnValue = "timeLimitConfig", allocationSize = 1, initialValue = 0)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "timeLimitConfig")
public class TimeLimitConfig implements ModificationModel<Long> {

	private static final long serialVersionUID = 9045901391530110050L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "timeLimitConfigGenerator")
	private Long id;

	/**
	 * 保存时
	 */
	@Column(name = "SAVE_LEFT")
	private int saveLeft = 700;

	/**
	 * 映射到本地产品时
	 */
	@Column(name = "MAP_LOCAL_LEFT")
	private int map2LocalLeft = 690;

	/**
	 * 映射到渠道产品时
	 */
	@Column(name = "MAP_CHANNEL_LEFT")
	private int map2ChannelLeft = 680;

	/**
	 * 重选渠道时，一个渠道处理订单失败后会进入此
	 */
	@Column(name = "CHOOSE_PROD_LEFT")
	private int chooseProductLeft = 670;

	/**
	 * 发送到渠道
	 */
	@Column(name = "SEND_CHANNEL_LEFT")
	private int sendToChannelLeft = 600;

	/**
	 * 完成，因为完成时，需要和淘宝同步订单状态，所以也需要时间
	 */
	@Column(name = "COMPLETE_LEFT")
	private int completeLeft = 120;

	@Embedded
	private Modification modification = Modification.createDefault();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public int getSaveLeft() {
		return saveLeft;
	}

	public void setSaveLeft(int saveLeft) {
		this.saveLeft = saveLeft;
	}

	public int getMap2LocalLeft() {
		return map2LocalLeft;
	}

	public void setMap2LocalLeft(int map2LocalLeft) {
		this.map2LocalLeft = map2LocalLeft;
	}

	public int getMap2ChannelLeft() {
		return map2ChannelLeft;
	}

	public void setMap2ChannelLeft(int map2ChannelLeft) {
		this.map2ChannelLeft = map2ChannelLeft;
	}

	public int getChooseProductLeft() {
		return chooseProductLeft;
	}

	public void setChooseProductLeft(int chooseProductLeft) {
		this.chooseProductLeft = chooseProductLeft;
	}

	public int getSendToChannelLeft() {
		return sendToChannelLeft;
	}

	public void setSendToChannelLeft(int sendToChannelLeft) {
		this.sendToChannelLeft = sendToChannelLeft;
	}

	public int getCompleteLeft() {
		return completeLeft;
	}

	public void setCompleteLeft(int completeLeft) {
		this.completeLeft = completeLeft;
	}

	@Override
	public Modification getModification() {
		return modification;
	}

	@Override
	public void setModification(Modification modification) {
		this.modification = modification;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chooseProductLeft;
		result = prime * result + completeLeft;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + map2ChannelLeft;
		result = prime * result + map2LocalLeft;
		result = prime * result + ((modification == null) ? 0 : modification.hashCode());
		result = prime * result + saveLeft;
		result = prime * result + sendToChannelLeft;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeLimitConfig other = (TimeLimitConfig) obj;
		if (chooseProductLeft != other.chooseProductLeft)
			return false;
		if (completeLeft != other.completeLeft)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (map2ChannelLeft != other.map2ChannelLeft)
			return false;
		if (map2LocalLeft != other.map2LocalLeft)
			return false;
		if (modification == null) {
			if (other.modification != null)
				return false;
		} else if (!modification.equals(other.modification))
			return false;
		if (saveLeft != other.saveLeft)
			return false;
		if (sendToChannelLeft != other.sendToChannelLeft)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeLimitConfig [id=" + id + ", saveLeft=" + saveLeft + ", map2LocalLeft=" + map2LocalLeft + ", map2ChannelLeft="
				+ map2ChannelLeft + ", chooseProductLeft=" + chooseProductLeft + ", sendToChannelLeft=" + sendToChannelLeft
				+ ", completeLeft=" + completeLeft + ", modification=" + modification + "]";
	}

}
