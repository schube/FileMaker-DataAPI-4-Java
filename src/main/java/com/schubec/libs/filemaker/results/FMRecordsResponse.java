package com.schubec.libs.filemaker.results;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMRecordsResponse {

	@JsonProperty("scriptResult")
	private String scriptResult;
	
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
	private List<FMRecord> records;

	public Optional<FMRecord> getFirstRecord() {
		if (records == null || records.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(records.get(0));
	}

	public List<FMRecord> getRecords() {
		if (records == null) {
			return Collections.emptyList();
		}
		return records;
	}

	public boolean hasRecords() {
		if (records == null || records.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void setRecords(List<FMRecord> data) {
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

	public String getScriptResult() {
		return scriptResult;
	}

	public void setScriptResult(String scriptResult) {
		this.scriptResult = scriptResult;
	}
}
