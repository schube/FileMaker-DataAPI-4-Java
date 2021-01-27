package com.schubec.libs.filemaker.results;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	@JsonProperty("recordId")
	private long recordId;

	@JsonProperty("modId")
	private long modId;

	@JsonProperty("portalData")
	@JsonIgnore
	private List<String> portalData;

	@JsonProperty("fieldData")
	private Map<String, Object> fieldData;

	public Map<String, Object> getFieldData() {
		return fieldData;
	}

	public void setFieldData(Map<String, Object> fieldData) {
		this.fieldData = fieldData;
	}

}
