package com.its.thread.models;

import java.io.Serializable;

public class RequestThreadModel implements Serializable {
	private String threadId;
	private String userName;

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
