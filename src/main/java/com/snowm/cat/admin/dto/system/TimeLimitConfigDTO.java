package com.snowm.cat.admin.dto.system;

import java.io.Serializable;

public class TimeLimitConfigDTO implements Serializable {

	private static final long serialVersionUID = -4732838601343038750L;

	private Long id;

	private int saveLeft = 700;

	private int map2LocalLeft = 690;

	private int map2ChannelLeft = 680;

	private int chooseProductLeft = 670;

	private int sendToChannelLeft = 600;

	private int completeLeft = 120;

	public Long getId() {
		return id;
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chooseProductLeft;
		result = prime * result + completeLeft;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + map2ChannelLeft;
		result = prime * result + map2LocalLeft;
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
		TimeLimitConfigDTO other = (TimeLimitConfigDTO) obj;
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
		if (saveLeft != other.saveLeft)
			return false;
		if (sendToChannelLeft != other.sendToChannelLeft)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeLimitConfigDTO [id=" + id + ", saveLeft=" + saveLeft + ", map2LocalLeft=" + map2LocalLeft + ", map2ChannelLeft="
				+ map2ChannelLeft + ", chooseProductLeft=" + chooseProductLeft + ", sendToChannelLeft=" + sendToChannelLeft
				+ ", completeLeft=" + completeLeft + "]";
	}

}
