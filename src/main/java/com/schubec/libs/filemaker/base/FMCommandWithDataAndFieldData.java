package com.schubec.libs.filemaker.base;

import java.util.Date;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.FMSession;

public abstract class FMCommandWithDataAndFieldData extends FMCommandWithData {
	private boolean returnRecord = false;
	protected final ObjectNode fieldData;

	public FMCommandWithDataAndFieldData(String layout) {
		super(layout);
		fieldData = mapper.createObjectNode();
	}

	public boolean isReturnRecord() {
		return returnRecord;
	}

	public void setReturnRecord(boolean returnRecord) {
		this.returnRecord = returnRecord;
	}
	public FMCommandWithDataAndFieldData setField(String fieldname, String value) {
		fieldData.put(fieldname, value);
		return this;
	}

	public FMCommandWithDataAndFieldData setDateField(String fieldname, Date value) {

		fieldData.put(fieldname, FMSession.DF_DATE.format(value));
		return this;
	}

	public FMCommandWithDataAndFieldData setTimestampField(String fieldname, Date value) {

		fieldData.put(fieldname, FMSession.DF_TIMESTAMP.format(value));
		return this;
	}
}
