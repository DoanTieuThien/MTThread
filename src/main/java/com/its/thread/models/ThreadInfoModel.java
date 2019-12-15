package com.its.thread.models;

import java.io.Serializable;
import java.util.HashMap;

import com.its.thread.abstracts.AbstractThreadBase;

public class ThreadInfoModel implements Serializable {
	private String threadName;
	private String threadClass;
	private String startupType;
	private HashMap parameters;
	private String threadState;
	private String message;
	private AbstractThreadBase thread; 
	private String threadId;
	private String prepareState;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getThreadClass() {
		return threadClass;
	}

	public void setThreadClass(String threadClass) {
		this.threadClass = threadClass;
	}

	public String getStartupType() {
		return startupType;
	}

	public void setStartupType(String startupType) {
		this.startupType = startupType;
	}

	public HashMap getParameters() {
		return parameters;
	}

	public void setParameters(HashMap parameters) {
		this.parameters = parameters;
	}

	public String getThreadState() {
		return threadState;
	}

	public void setThreadState(String threadState) {
		this.threadState = threadState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AbstractThreadBase getThread() {
		return thread;
	}

	public void setThread(AbstractThreadBase thread) {
		this.thread = thread;
	}

	public String getThreadId() {
		if(this.thread != null) {
			this.threadId = this.thread.threadId();
		}
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getPrepareState() {
		return prepareState;
	}

	public void setPrepareState(String prepareState) {
		this.prepareState = prepareState;
	}
}
