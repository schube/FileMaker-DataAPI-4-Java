package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a message returned by the FileMaker Data API.
 * Contains a code and a message string.
 */
public class Message {
	private int code;
	private String message;

	/**
	 * Returns the error or status code for this message.
	 * @return The code.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the error or status code for this message.
	 * @param code The code.
	 */
	@JsonProperty("code")
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Returns the message string.
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message string.
	 * @param message The message.
	 */
	@JsonProperty("message")
	public void setMessage(String message) {
		this.message = message;
	}
}
