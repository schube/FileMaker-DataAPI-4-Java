package com.schubec.libs.filemaker.implementation;

import java.net.URI;
import java.util.Date;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.base.FMCommandWithDataAndFieldData;

/**
 * Command for adding a new record to a FileMaker layout.
 * Supports returning the created record if desired.
 */
public class FMAddCommand extends FMCommandWithDataAndFieldData {

	/**
	 * Constructs a new FMAddCommand for the given layout.
	 * @param layout The layout name.
	 */
	public FMAddCommand(String layout) {
		this(layout, false);
	}

	/**
	 * Constructs a new FMAddCommand for the given layout, with option to return the record.
	 * @param layout The layout name.
	 * @param returnRecord true to return the created record, false otherwise.
	 */
	public FMAddCommand(String layout, boolean returnRecord) {
		super(layout);

		setReturnRecord(returnRecord);
	}

	/**
	 * Returns the endpoint path for this command.
	 * @return The endpoint path as a String.
	 */
	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records";
	}

	/**
	 * Returns the HTTP command object for this command.
	 * @param uri The URI to use for the request.
	 * @return The HTTP POST request object.
	 */
	@Override
	public HttpEntityEnclosingRequestBase getHttpCommand(URI uri) {
		return new HttpPost(uri);
	}

	/**
	 * Builds the command's JSON body with field data.
	 */
	@Override
	protected void buildCommand() {
		getFmJsonObject().set("fieldData", fieldData);

	}
}
