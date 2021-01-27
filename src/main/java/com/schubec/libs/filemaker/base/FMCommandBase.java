package com.schubec.libs.filemaker.base;

import java.net.URI;

import org.apache.http.client.methods.HttpRequestBase;

public abstract class FMCommandBase {
	private String layout;

	public abstract String getEndpoint();

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
	public abstract HttpRequestBase getHttpCommand(URI uri);
	
	
	
}
