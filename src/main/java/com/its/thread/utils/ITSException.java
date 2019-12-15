package com.its.thread.utils;

public class ITSException extends Exception {
	private String code = "";
	private String message = "";
	private Exception exp = null;

	public ITSException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ITSException(String code, Exception exp) {
		this.code = code;
		this.exp = exp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getExp() {
		return exp;
	}

	public void setExp(Exception exp) {
		this.exp = exp;
	}
}
