package com.woyao.customer.disruptor;

import java.io.Serializable;

public class LongEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long value;

	public LongEvent() {
		super();
	}

	public LongEvent(long value) {
		super();
		this.value = value;
	}

	public void set(long value) {
		this.value = value;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

}
