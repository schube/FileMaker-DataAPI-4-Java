package com.schubec.libs.filemaker.implementation;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPatch;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.base.FMCommandWithData;

public class FMEditCommand extends FMCommandWithData {

	private long recordId;
	private final ObjectNode fieldData;

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public FMEditCommand(String layout, long recordId) {
		super(layout);
		setRecordId(recordId);
		fieldData = mapper.createObjectNode();

	}

	public FMEditCommand setField(String fieldname, String value) {
		fieldData.put(fieldname, value);
		return this;
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
