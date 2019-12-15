package com.its.thread.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class RequestModel implements Serializable {
	private String commandName;
	private String threadId;
	private HashMap parameters;
	private Date eventDate;

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public HashMap getParameters() {
		return parameters;
	}

	public void setParameters(HashMap parameters) {
		this.parameters = parameters;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
}
