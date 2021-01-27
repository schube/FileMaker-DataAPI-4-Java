package com.schubec.libs.filemaker.base;

import java.net.URI;

import org.apache.http.client.methods.HttpRequestBase;

public abstract class FMCommandWithoutBody extends FMCommandBase {

	public FMCommandWithoutBody(String layout) {
		setLayout(layout);
	}
	
	
	
	
}
