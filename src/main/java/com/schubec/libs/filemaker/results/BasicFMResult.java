package com.schubec.libs.filemaker.results;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Abstract base class for FileMaker API result objects.
 * Provides HTTP status, request URI, and response body tracking.
 */
public abstract class BasicFMResult {
	@JsonIgnore
	private String httpBody;

	@JsonIgnore
	private URI requestUri;
	
	@JsonIgnore
	private int httpStatusCode;

	/**
	 * Returns the HTTP status code for the API response.
	 * @return The HTTP status code.
	 */
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	/**
	 * Sets the HTTP status code for the API response.
	 * @param httpStatusCode The HTTP status code.
	 */
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * Sets the raw HTTP response body string.
	 * @param httpBody The response body string.
	 */
	public void setHttpBodyString(String httpBody) {
		this.httpBody = httpBody;
	}

	/**
	 * Returns the raw HTTP response body string.
	 * @return The response body string.
	 */
	public String getHttpBody() {
		return httpBody;
	}

	/**
	 * Returns the request URI for the API call.
	 * @return The request URI.
	 */
	public URI getRequestUri() {
		return requestUri;
	}

	/**
	 * Sets the request URI for the API call.
	 * @param requestUri The request URI.
	 */
	public void setRequestUri(URI requestUri) {
		this.requestUri = requestUri;
	}
}
