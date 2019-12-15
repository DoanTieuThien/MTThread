package com.its.thread.utils;

public class Tool {
	public static long loadLongValue(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
		}
		return 0L;
	}
	
	public static int loadIntValue(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
		}
		return 0;
	}
}
