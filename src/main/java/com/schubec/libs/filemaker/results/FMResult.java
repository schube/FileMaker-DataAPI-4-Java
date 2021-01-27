package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FMResult {

	@JsonIgnore
	private int httpStatusCode;
	
	@JsonProperty("messages")
	private Message[] messages;

	@JsonProperty("response")
	private FMResponse response;

	
	
	public static FMResult empty() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	@JsonIgnore
	public boolean isSuccess() {
		return httpStatusCode==200;
	}

	public FMResponse getResponse() {
		return response;
	}

	public void setResponse(FMResponse response) {
		this.response = response;
	}

	public Message[] getMessages() {
		return messages;
	}

	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

	public String getMessagesAsString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP Status Code ["+getHttpStatusCode()+"].\n");
		for(Message message : messages) {
			sb.append(" Message ["+message.getMessage()+"] / Code ["+message.getCode()+"].\n");	
		}
		
		return sb.toString();
		
	}

	
	
}
