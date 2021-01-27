package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}
}
