package com.schubec.libs.filemaker.results;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response for a FileMaker records query.
 * Contains record data, script results, and metadata.
 */
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

	/**
	 * Returns the first record in the response, if present.
	 * @return An Optional containing the first FMRecord, or empty if none.
	 */
	public Optional<FMRecord> getFirstRecord() {
		if (records == null || records.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(records.get(0));
	}

	/**
	 * Returns the list of records in the response.
	 * @return The list of FMRecord objects.
	 */
	public List<FMRecord> getRecords() {
		if (records == null) {
			return Collections.emptyList();
		}
		return records;
	}

	/**
	 * Returns true if the response contains any records.
	 * @return true if records are present, false otherwise.
	 */
	public boolean hasRecords() {
		if (records == null || records.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Sets the list of records in the response.
	 * @param data The list of FMRecord objects.
	 */
	public void setRecords(List<FMRecord> data) {
		this.records = data;
	}

	/**
	 * Returns the DataInfo object for this response.
	 * @return The DataInfo object.
	 */
	public DataInfo getDataInfo() {
		return dataInfo;
	}

	/**
	 * Sets the DataInfo object for this response.
	 * @param dataInfo The DataInfo object.
	 */
	public void setDataInfo(DataInfo dataInfo) {
		this.dataInfo = dataInfo;
	}

	/**
	 * Returns the record ID for the response.
	 * @return The record ID.
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * Sets the record ID for the response.
	 * @param recordId The record ID.
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * Returns the modification ID for the response.
	 * @return The modification ID.
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * Sets the modification ID for the response.
	 * @param modId The modification ID.
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	/**
	 * Returns the script error message for the response.
	 * @return The script error message.
	 */
	public String getScriptError() {
		return scriptError;
	}

	/**
	 * Sets the script error message for the response.
	 * @param scriptError The script error message.
	 */
	public void setScriptError(String scriptError) {
		this.scriptError = scriptError;
	}

	/**
	 * Returns the script result for the response.
	 * @return The script result.
	 */
	public String getScriptResult() {
		return scriptResult;
	}

	/**
	 * Sets the script result for the response.
	 * @param scriptResult The script result.
	 */
	public void setScriptResult(String scriptResult) {
		this.scriptResult = scriptResult;
	}
}
