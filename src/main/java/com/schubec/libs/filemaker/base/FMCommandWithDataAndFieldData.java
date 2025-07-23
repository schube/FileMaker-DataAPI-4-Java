package com.schubec.libs.filemaker.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.FMSession;

public abstract class FMCommandWithDataAndFieldData extends FMCommandWithData {
	private boolean returnRecord = false;
	protected final ObjectNode fieldData;
	protected DateFormat DF_DATE = null;
	protected DateFormat DF_TIMESTAMP = null;

	private DateFormat getDateFormater() {
		if (DF_DATE == null) {
			DF_DATE = new SimpleDateFormat(FMSession.DATE_PATTERN);
		}
		return DF_DATE;
	}

	private DateFormat getDateTimeFormater() {
		if (DF_TIMESTAMP == null) {
			DF_TIMESTAMP = new SimpleDateFormat(FMSession.TIMESTAMP_PATTERN);
		}
		return DF_TIMESTAMP;
	}

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

	public FMCommandWithDataAndFieldData setField(String fieldname, String value, String defaultValue) {
		if (value == null) {
			fieldData.put(fieldname, defaultValue);
		} else {
			fieldData.put(fieldname, value);
		}
		return this;
	}

	public FMCommandWithDataAndFieldData setField(String fieldname, String value) {
		fieldData.put(fieldname, value);
		return this;
	}

	public FMCommandWithDataAndFieldData setDateField(String fieldname, Date value, String defaultValue) {
		if (value == null) {
			fieldData.put(fieldname, defaultValue);
		} else {
			fieldData.put(fieldname, getDateFormater().format(value));
		}
		return this;
	}

	public FMCommandWithDataAndFieldData setDateField(String fieldname, Date value) {
		fieldData.put(fieldname, getDateFormater().format(value));
		return this;
	}

	public FMCommandWithDataAndFieldData setTimestampField(String fieldname, Date value, String defaultValue) {
		if (value == null) {
			fieldData.put(fieldname, defaultValue);
		} else {
			fieldData.put(fieldname, getDateTimeFormater().format(value));
		}
		return this;
	}

	public FMCommandWithDataAndFieldData setTimestampField(String fieldname, Date value) {

		fieldData.put(fieldname, getDateTimeFormater().format(value));
		return this;
	}
}
