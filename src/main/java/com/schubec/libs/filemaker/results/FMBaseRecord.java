package com.schubec.libs.filemaker.results;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schubec.libs.filemaker.FMSession;

/**
 * Base class for FileMaker record objects.
 * Provides field access and type conversion utilities.
 */
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

	/**
	 * Returns the set of available field names in this record.
	 * @return The set of field names.
	 */
	public Set<String> getAvailableFields() {
		return fieldData.keySet();
	}

	/**
	 * Returns the map of field data for this record.
	 * @return The map of field names to values.
	 */
	public Map<String, String> getFieldData() {
		return fieldData;
	}

	/**
	 * Sets the field data for this record.
	 * @param fieldData The map of field names to values.
	 */
	public void setFieldData(Map<String, String> fieldData) {
		this.fieldData = fieldData;
	}

	/**
	 * Returns the value of a field by name.
	 * @param key The field name.
	 * @return The field value, or null if not present.
	 */
	public String getField(String key) {
		return fieldData.get(key);
	}

	/**
	 * Returns the value of a field by name and repetition.
	 * @param key The field name.
	 * @param one_based_repetition The repetition number (1-based).
	 * @return The field value, or null if not present.
	 */
	public String getField(String key, int one_based_repetition) {
		if (one_based_repetition == 1) {
			getField(key);
		}
		return fieldData.get(key + "(" + one_based_repetition + ")");
	}

	/**
	 * Returns the value of a field as a Date.
	 * @param key The field name.
	 * @return The field value as a Date, or null if not present or parse fails.
	 */
	public Date getFieldAsDate(String key) {
		return getFieldAsDate(key, null);
	}

	/**
	 * Returns the value of a field as a Date, or a default if not present or parse fails.
	 * @param key The field name.
	 * @param defaultValue The default value to return if parse fails.
	 * @return The field value as a Date, or defaultValue.
	 */
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

	/**
	 * Returns the value of a field as a Timestamp (Date).
	 * @param key The field name.
	 * @return The field value as a Date, or null if not present or parse fails.
	 */
	public Date getFieldAsTimestamp(String key) {
		return getFieldAsTimestamp(key, null);
	}

	/**
	 * Returns the value of a field as a Timestamp (Date), or a default if not present or parse fails.
	 * @param key The field name.
	 * @param defaultValue The default value to return if parse fails.
	 * @return The field value as a Date, or defaultValue.
	 */
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

	/**
	 * Returns the value of a field as a Boolean.
	 * @param key The field name.
	 * @return The field value as a Boolean, or false if not present.
	 */
	public Boolean getFieldAsBoolean(String key) {
		return getFieldAsBoolean(key, null);
	}

	/**
	 * Returns the value of a field as a Boolean, or a default if not present.
	 * @param key The field name.
	 * @param defaultValue The default value to return if not present.
	 * @return The field value as a Boolean, or defaultValue.
	 */
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

	/**
	 * Returns the value of a field as an Integer.
	 * @param key The field name.
	 * @return The field value as an Integer, or null if not present or parse fails.
	 */
	public Integer getFieldAsInteger(String key) {
		return getFieldAsInteger(key, null);
	}

	/**
	 * Returns the value of a field as an Integer, or a default if not present or parse fails.
	 * @param key The field name.
	 * @param defaultValue The default value to return if parse fails.
	 * @return The field value as an Integer, or defaultValue.
	 */
	public Integer getFieldAsInteger(String key, Integer defaultValue) {
		String value = fieldData.get(key);
		if (value == null || value.equals("")) {
			return null;
		}
		return Integer.parseInt(value);
	}

	/**
	 * Returns the value of a field as a Double.
	 * @param key The field name.
	 * @return The field value as a Double, or null if not present or parse fails.
	 */
	public Double getFieldAsDouble(String key) {
		return getFieldAsDouble(key, null);
	}

	/**
	 * Returns the value of a field as a Double, or a default if not present or parse fails.
	 * @param key The field name.
	 * @param defaultValue The default value to return if parse fails.
	 * @return The field value as a Double, or defaultValue.
	 */
	public Double getFieldAsDouble(String key, Double defaultValue) {
		String value = fieldData.get(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		return Double.parseDouble(value);
	}

	/**
	 * Returns the value of a field as a Float.
	 * @param key The field name.
	 * @return The field value as a Float, or null if not present or parse fails.
	 */
	public Float getFieldAsFloat(String key) {
		return getFieldAsFloat(key, null);
	}

	/**
	 * Returns the value of a field as a Float, or a default if not present or parse fails.
	 * @param key The field name.
	 * @param defaultValue The default value to return if parse fails.
	 * @return The field value as a Float, or defaultValue.
	 */
	public Float getFieldAsFloat(String key, Float defaultValue) {
		String value = fieldData.get(key);
		if (value == null || value.equals("")) {
			return defaultValue;
		}
		return Float.parseFloat(value);
	}

	/**
	 * Returns whether this record contains the given field.
	 * @param key The field name.
	 * @return true if the field exists, false otherwise.
	 */
	public boolean hasField(String key) {
		return fieldData.containsKey(key);
	}
}
