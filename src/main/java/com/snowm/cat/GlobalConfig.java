package com.snowm.cat;

import java.io.Serializable;

public class GlobalConfig implements Serializable {

	private static final long serialVersionUID = 3659255961178735436L;

	private String supplierId;

	private String privateKey;

	private int aliConnectionTimeout;

	private int aliSocketTimeout;

	private int aliConnectionRequestTimeout;

	private int channelTimeout;

	private boolean serviceAvailable = true;

	private int submitOrderConsumerNum = 5;

	private int sendAliCallbackConsumerNum = 5;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public int getAliConnectionTimeout() {
		return aliConnectionTimeout;
	}

	public void setAliConnectionTimeout(int aliConnectionTimeout) {
		this.aliConnectionTimeout = aliConnectionTimeout;
	}

	public int getAliSocketTimeout() {
		return aliSocketTimeout;
	}

	public void setAliSocketTimeout(int aliSocketTimeout) {
		this.aliSocketTimeout = aliSocketTimeout;
	}

	public int getAliConnectionRequestTimeout() {
		return aliConnectionRequestTimeout;
	}

	public void setAliConnectionRequestTimeout(int aliConnectionRequestTimeout) {
		this.aliConnectionRequestTimeout = aliConnectionRequestTimeout;
	}

	public int getChannelTimeout() {
		return channelTimeout;
	}

	public void setChannelTimeout(int channelTimeout) {
		this.channelTimeout = channelTimeout;
	}

	public boolean isServiceAvailable() {
		return serviceAvailable;
	}

	public void setServiceAvailable(boolean serviceAvailable) {
		this.serviceAvailable = serviceAvailable;
	}

	public int getSubmitOrderConsumerNum() {
		return submitOrderConsumerNum;
	}

	public void setSubmitOrderConsumerNum(int submitOrderConsumerNum) {
		this.submitOrderConsumerNum = submitOrderConsumerNum;
	}

	public int getSendAliCallbackConsumerNum() {
		return sendAliCallbackConsumerNum;
	}

	public void setSendAliCallbackConsumerNum(int sendAliCallbackConsumerNum) {
		this.sendAliCallbackConsumerNum = sendAliCallbackConsumerNum;
	}

}
