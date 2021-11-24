package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataInfo {
	@JsonProperty("totalRecordCount")
	private long totalRecordCount=0;

	@JsonProperty("foundCount")
	private long foundCount=0;

	@JsonProperty("returnedCount")
	private long returnedCount=0;

	@JsonProperty("table")
	private String table;

	@JsonProperty("database")
	private String database;

	@JsonProperty("layout")
	private String layout;

	public long getFoundCount() {
		return foundCount;
	}

	public void setFoundCount(long foundCount) {
		this.foundCount = foundCount;
	}

	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public long getReturnedCount() {
		return returnedCount;
	}

	public void setReturnedCount(long returnedCount) {
		this.returnedCount = returnedCount;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
