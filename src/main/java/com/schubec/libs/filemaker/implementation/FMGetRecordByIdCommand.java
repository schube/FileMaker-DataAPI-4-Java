package com.schubec.libs.filemaker.implementation;

import java.net.URI;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import com.schubec.libs.filemaker.base.FMCommandWithoutBody;

public class FMGetRecordByIdCommand extends FMCommandWithoutBody {

	private long recordId;

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public FMGetRecordByIdCommand(String layout, long recordId) {
		super(layout);
		setRecordId(recordId);

	}

	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records/" + getRecordId();
	}

	@Override
	public HttpRequestBase getHttpCommand(URI uri) {
		return new HttpGet(uri);
	}

}
