package com.schubec.libs.filemaker.implementation;

import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import com.schubec.libs.filemaker.base.FMCommandBase;

public class FMListScriptsCommand extends FMCommandBase {

	public String getEndpoint() {
		return "/scripts";
	}

	@Override
	public HttpRequestBase getHttpCommand(URI uri) {
		return new HttpGet(uri);
	}

	@Override
	public void prepareCommand() {
		// Nothing to do
		
	}

}
