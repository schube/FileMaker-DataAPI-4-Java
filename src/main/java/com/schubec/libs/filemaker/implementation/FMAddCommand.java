package com.schubec.libs.filemaker.implementation;

import java.net.URI;
import java.util.Date;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.base.FMCommandWithDataAndFieldData;

public class FMAddCommand extends FMCommandWithDataAndFieldData {

	public FMAddCommand(String layout) {
		this(layout, false);
	}

	public FMAddCommand(String layout, boolean returnRecord) {
		super(layout);

		setReturnRecord(returnRecord);
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
