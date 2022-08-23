package com.schubec.libs.filemaker.results;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schubec.libs.filemaker.FMSession;

public class FMBaseRecord {
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

	@JsonProperty("fieldData")
	protected Map<String, String> fieldData;

	public Set<String> getAvailableFields() {
		return fieldData.keySet();
	}

	public Map<String, String> getFieldData() {
		return fieldData;
	}

	public void setFieldData(Map<String, String> fieldData) {
		this.fieldData = fieldData;
	}

	public String getField(String key) {
		return fieldData.get(key);
	}

	public String getField(String key, int one_based_repetition) {
		if (one_based_repetition == 1) {
			getField(key);
		}
		return fieldData.get(key + "(" + one_based_repetition + ")");
	}

	public Date getFieldAsDate(String key) {
		return getFieldAsDate(key, null);
	}

	public Date getFieldAsDate(String key, Date defaultValue) {
		String value = getField(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		try {
			return getDateFormater().parse(value);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	public Date getFieldAsTimestamp(String key) {
		return getFieldAsTimestamp(key, null);
	}

	public Date getFieldAsTimestamp(String key, Date defaultValue) {
		String value = getField(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		try {
			return getDateTimeFormater().parse(value);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	public Boolean getFieldAsBoolean(String key) {
		return getFieldAsBoolean(key, null);
	}

	public Boolean getFieldAsBoolean(String key, Boolean defaultValue) {
		String value = getField(key);
		if (value == null || value.length() == 0) {
			return false;
		}
		if (value.equals("1")) {
			return true;
		}
		return Boolean.parseBoolean(value);
	}

	public Integer getFieldAsInteger(String key) {
		return getFieldAsInteger(key, null);
	}

	public Integer getFieldAsInteger(String key, Integer defaultValue) {
		String value = fieldData.get(key);
		if (value == null || value.equals("")) {
			return null;
		}
		return Integer.parseInt(value);
	}

	public Double getFieldAsDouble(String key) {
		return getFieldAsDouble(key, null);
	}

	public Double getFieldAsDouble(String key, Double defaultValue) {
		String value = fieldData.get(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		return Double.parseDouble(value);
	}

	public Float getFieldAsFloat(String key) {
		return getFieldAsFloat(key, null);
	}

	public Float getFieldAsFloat(String key, Float defaultValue) {
		String value = fieldData.get(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		return Float.parseFloat(value);
	}

	public boolean hasField(String key) {
		return fieldData.containsKey(key);
	}
}
