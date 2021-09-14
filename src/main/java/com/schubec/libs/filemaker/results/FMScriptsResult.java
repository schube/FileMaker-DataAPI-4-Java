package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FMScriptsResult {

	@JsonIgnore
	private int httpStatusCode;

	@JsonProperty("messages")
	private Message[] messages;

	@JsonProperty("response")
	private FMScriptsResponse response;

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	@JsonIgnore
	public boolean isSuccess() {
		return httpStatusCode == 200;
	}

	public FMScriptsResponse getResponse() {
		return response;
	}

	public Message[] getMessages() {
		return messages;
	}

	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

	public String getMessagesAsString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP Status Code [" + getHttpStatusCode() + "].\n");
		for (Message message : messages) {
			sb.append(" Message [" + message.getMessage() + "] / Code [" + message.getCode() + "].\n");
		}

		return sb.toString();

	}

}
