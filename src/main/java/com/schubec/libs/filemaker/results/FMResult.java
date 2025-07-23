package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Generic result wrapper for FileMaker API responses.
 * Contains response data and messages.
 * @param <T> The type of the response data.
 */
public class FMResult<T> extends BasicFMResult {

	@JsonProperty("messages")
	private Message[] messages;

	@JsonProperty("response")
	private T response;

	/**
	 * Returns the response data object.
	 * @return The response data.
	 */
	public T getResponse() {
		return response;
	}

	/**
	 * Returns the array of messages from the API response.
	 * @return The array of Message objects.
	 */
	public Message[] getMessages() {
		return messages;
	}

	/**
	 * Sets the array of messages for the API response.
	 * @param messages The array of Message objects.
	 */
	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

	/**
	 * Returns the messages as a formatted string.
	 * @return The messages as a string.
	 */
	public String getMessagesAsString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP Status Code [" + getHttpStatusCode() + "].\n");
		for (Message message : messages) {
			sb.append(" Message [" + message.getMessage() + "] / Code [" + message.getCode() + "].\n");
		}

		return sb.toString();

	}

}
