package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMResponse {

	@JsonProperty("scriptError")
	private String scriptError;

	@JsonProperty("scriptError.presort")
	private String scriptErrorPreSort;

	@JsonProperty("scriptError.prerequest")
	private String scriptErrorPreRequest;

	@JsonProperty("recordId")
	private String recordId;

	@JsonProperty("modId")
	private String modId;

	@JsonProperty("dataInfo")
	private DataInfo dataInfo;

	@JsonProperty("data")
	private FMRecord[] records;

	public FMRecord getFirstRecord() {
		return records[0];
	}

	public FMRecord[] getRecords() {
		return records;
	}

	public void setRecords(FMRecord[] data) {
		this.records = data;
	}

	public DataInfo getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(DataInfo dataInfo) {
		this.dataInfo = dataInfo;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getScriptError() {
		return scriptError;
	}

	public void setScriptError(String scriptError) {
		this.scriptError = scriptError;
	}
}
