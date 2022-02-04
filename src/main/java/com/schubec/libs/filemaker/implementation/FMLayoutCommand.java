package com.schubec.libs.filemaker.implementation;

import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import com.schubec.libs.filemaker.base.FMCommandWithoutBody;

public class FMLayoutCommand extends FMCommandWithoutBody {

	public FMLayoutCommand(String layout) {
		super(layout);
	}

	public String getEndpoint() {
		return "/layouts/" + getLayout();
	}

	@Override
	public HttpRequestBase getHttpCommand(URI uri) {
		return new HttpGet(uri);
	}

}
