package com.schubec.libs.filemaker.results;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.schubec.libs.filemaker.FMSession;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Record {

	@JsonProperty("recordId")
	private long recordId;

	@JsonProperty("modId")
	private long modId;

	@JsonProperty("portalData")
	@JsonIgnore
	private List<String> portalData;

	@JsonProperty("fieldData")
	private Map<String, String> fieldData;

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
		if(one_based_repetition==1) {
			getField(key);
		}
		return fieldData.get(key + "("+one_based_repetition+")");
	}
	
	public Date getDateField(String key) throws ParseException {
		String value = getField(key);
		if (value == null || value.equals("")) {
			return null;
		}
		return FMSession.DF_DATE.parse(value);
	}

	public Date getTimestampField(String key) throws ParseException {
		String value = getField(key);
		if (value == null || value.equals("")) {
			return null;
		}
		return FMSession.DF_TIMESTAMP.parse(value);
	}

	public Boolean getFieldAsBoolean(String key) {
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
		return Integer.parseInt(fieldData.get(key));
	}

	public Double getFieldAsDouble(String key) {
		return Double.parseDouble(fieldData.get(key));
	}

	public Float getFieldAsFloat(String key) {
		return Float.parseFloat(fieldData.get(key));
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public long getModId() {
		return modId;
	}

	public void setModId(long modId) {
		this.modId = modId;
	}
	public boolean hasField(String key) {
		return fieldData.containsKey(key);
	}

}
