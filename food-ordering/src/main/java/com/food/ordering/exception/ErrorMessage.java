package com.food.ordering.exception;

import java.time.LocalDateTime;

public class ErrorMessage {
	private LocalDateTime timeStamp;
	private String message;
	private int statusCode;

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "ErrorMessage [timeStamp=" + timeStamp + ", message=" + message + ", statusCode=" + statusCode + "]";
	}

	public ErrorMessage() {
	
	}

	public ErrorMessage(LocalDateTime timeStamp, String message, int statusCode) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.statusCode = statusCode;
	}
	

}
