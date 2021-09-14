package com.schubec.libs.filemaker.implementation;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPatch;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.base.FMCommandWithDataAndFieldData;

public class FMEditCommand extends FMCommandWithDataAndFieldData {

	private long recordId;

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public FMEditCommand(String layout, long recordId) {
		this(layout, recordId, false);
	}

	public FMEditCommand(String layout, long recordId, boolean returnRecord) {
		super(layout);
		setRecordId(recordId);

		setReturnRecord(returnRecord);
	}

	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records/" + getRecordId();
	}

	@Override
	public HttpEntityEnclosingRequestBase getHttpCommand(URI uri) {
		return new HttpPatch(uri);
	}

	@Override
	protected void buildCommand() {
		getFmJsonObject().set("fieldData", fieldData);

	}

}
