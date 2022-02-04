package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMResult<T> extends BasicFMResult {

	@JsonProperty("messages")
	private Message[] messages;

	@JsonProperty("response")
	private T response;

	public T getResponse() {
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
