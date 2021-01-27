package com.schubec.libs.filemaker.implementation;

import java.net.URI;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

import com.schubec.libs.filemaker.base.FMCommandWithoutBody;

public class FMDeleteCommand extends FMCommandWithoutBody {

	private long recordId;

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public FMDeleteCommand(String layout, long recordId) {
		super(layout);
		setRecordId(recordId);

	}

	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records/" + getRecordId();
	}

	@Override
	public HttpRequestBase getHttpCommand(URI uri) {
		return new HttpDelete(uri);
	}

}
