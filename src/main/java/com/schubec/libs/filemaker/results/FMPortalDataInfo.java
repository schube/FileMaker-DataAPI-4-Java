package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMPortalDataInfo {
	@JsonProperty("database")
	private String database;

	@JsonProperty("table")
	private String table;

	@JsonProperty("foundCount")
	private long foundCount;

	@JsonProperty("returnedCount")
	private long returnedCount;

	@JsonProperty("portalObjectName")
	private String portalObjectName;
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public long getFoundCount() {
		return foundCount;
	}

	public void setFoundCount(long foundCount) {
		this.foundCount = foundCount;
	}

	public long getReturnedCount() {
		return returnedCount;
	}

	public void setReturnedCount(long returnedCount) {
		this.returnedCount = returnedCount;
	}

	public String getPortalObjectName() {
		return portalObjectName;
	}

	public void setPortalObjectName(String portalObjectName) {
		this.portalObjectName = portalObjectName;
	}
}
