package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMResponse {
	
	@JsonProperty("dataInfo")
	private DataInfo dataInfo;
	
	@JsonProperty("data")
	private Data[] data;
	
	@JsonProperty("recordId")
	private long recordId;

	@JsonProperty("modId")
	private long modId;

	

	
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
	public Data[] getData() {
		return data;
	}

	public void setData(Data[] data) {
		this.data = data;
	}

	public DataInfo getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(DataInfo dataInfo) {
		this.dataInfo = dataInfo;
	}
}
