package com.schubec.libs.filemaker.implementation;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.base.FMCommandWithData;

public class FMAddCommand extends FMCommandWithData {
	private final ObjectNode fieldData;

	public FMAddCommand(String layout) {
		super(layout);
		fieldData = mapper.createObjectNode();
	}

	public FMAddCommand setField(String fieldname, String value) {
		fieldData.put(fieldname, value);
		return this;
	}

	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records";
	}

	@Override
	public HttpEntityEnclosingRequestBase getHttpCommand(URI uri) {
		return new HttpPost(uri);
	}

	@Override
	protected void buildCommand() {
		getFmJsonObject().set("fieldData", fieldData);

	}
}
