package com.schubec.libs.filemaker.results;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BasicFMResult {
	@JsonIgnore
	private String httpBody;

	@JsonIgnore
	private URI requestUri;
	
	@JsonIgnore
	private int httpStatusCode;

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

	public void setHttpBodyString(String httpBody) {
		this.httpBody = httpBody;
	}

	public String getHttpBody() {
		return httpBody;
	}

	public URI getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(URI requestUri) {
		this.requestUri = requestUri;
	}
}
