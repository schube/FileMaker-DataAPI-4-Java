package com.schubec.libs.filemaker.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.schubec.libs.filemaker.FMSession;

/**
 * Abstract base class for FileMaker commands that include field data in the JSON body.
 * Provides methods for setting field and date values.
 */
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

	/**
	 * Constructor with layout name.
	 * @param layout The layout name.
	 */
	public FMCommandWithDataAndFieldData(String layout) {
		super(layout);
		fieldData = mapper.createObjectNode();
	}

	/**
	 * Returns whether the command should return the record after execution.
	 * @return true if the record should be returned, false otherwise.
	 */
	public boolean isReturnRecord() {
		return returnRecord;
	}

	/**
	 * Sets whether the command should return the record after execution.
	 * @param returnRecord true to return the record, false otherwise.
	 */
	public void setReturnRecord(boolean returnRecord) {
		this.returnRecord = returnRecord;
	}

	/**
	 * Sets a field value, using a default if the value is null.
	 * @param fieldname The field name.
	 * @param value The value to set.
	 * @param defaultValue The default value if value is null.
	 * @return This command instance.
	 */
	public FMCommandWithDataAndFieldData setField(String fieldname, String value, String defaultValue) {
		if (value == null) {
			fieldData.put(fieldname, defaultValue);
		} else {
			fieldData.put(fieldname, value);
		}
		return this;
	}

	/**
	 * Sets a field value.
	 * @param fieldname The field name.
	 * @param value The value to set.
	 * @return This command instance.
	 */
	public FMCommandWithDataAndFieldData setField(String fieldname, String value) {
		fieldData.put(fieldname, value);
		return this;
	}

	/**
	 * Sets a date field value, using a default if the value is null.
	 * @param fieldname The field name.
	 * @param value The date value to set.
	 * @param defaultValue The default value if value is null.
	 * @return This command instance.
	 */
	public FMCommandWithDataAndFieldData setDateField(String fieldname, Date value, String defaultValue) {
		if (value == null) {
			fieldData.put(fieldname, defaultValue);
		} else {
			fieldData.put(fieldname, getDateFormater().format(value));
		}
		return this;
	}

	/**
	 * Sets a date field value.
	 * @param fieldname The field name.
	 * @param value The date value to set.
	 * @return This command instance.
	 */
	public FMCommandWithDataAndFieldData setDateField(String fieldname, Date value) {
		fieldData.put(fieldname, getDateFormater().format(value));
		return this;
	}

	/**
	 * Sets a timestamp field value, using a default if the value is null.
	 * @param fieldname The field name.
	 * @param value The timestamp value to set.
	 * @param defaultValue The default value if value is null.
	 * @return This command instance.
	 */
	public FMCommandWithDataAndFieldData setTimestampField(String fieldname, Date value, String defaultValue) {
		if (value == null) {
			fieldData.put(fieldname, defaultValue);
		} else {
			fieldData.put(fieldname, getDateTimeFormater().format(value));
		}
		return this;
	}

	/**
	 * Sets a timestamp field value.
	 * @param fieldname The field name.
	 * @param value The timestamp value to set.
	 * @return This command instance.
	 */
	public FMCommandWithDataAndFieldData setTimestampField(String fieldname, Date value) {

		fieldData.put(fieldname, getDateTimeFormater().format(value));
		return this;
	}
}
