package com.schubec.libs.filemaker.results;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMResult extends BasicFMResult {

	@JsonProperty("messages")
	private Message[] messages;

	@JsonProperty("response")
	private FMResponse response;

	public DataInfo getDataInfo() {
		return getResponse().getDataInfo();
	}

	public boolean hasRecords() {
		return !getResponse().getRecords().isEmpty();
	}

	public Optional<FMRecord> getFirstRecord() {
		return getResponse().getFirstRecord();
	}

	public List<FMRecord> getRecords() {
		return getResponse().getRecords();
	}

	public FMResponse getResponse() {
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
