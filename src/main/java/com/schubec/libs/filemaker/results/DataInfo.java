package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Metadata about FileMaker data responses, including record counts and layout info.
 */
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

	/**
	 * Returns the number of found records.
	 * @return The found count.
	 */
	public long getFoundCount() {
		return foundCount;
	}

	/**
	 * Sets the number of found records.
	 * @param foundCount The found count.
	 */
	public void setFoundCount(long foundCount) {
		this.foundCount = foundCount;
	}

	/**
	 * Returns the total number of records in the table.
	 * @return The total record count.
	 */
	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * Sets the total number of records in the table.
	 * @param totalRecordCount The total record count.
	 */
	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	/**
	 * Returns the number of records returned in the response.
	 * @return The returned count.
	 */
	public long getReturnedCount() {
		return returnedCount;
	}

	/**
	 * Sets the number of records returned in the response.
	 * @param returnedCount The returned count.
	 */
	public void setReturnedCount(long returnedCount) {
		this.returnedCount = returnedCount;
	}

	/**
	 * Returns the table name for the response.
	 * @return The table name.
	 */
	public String getTable() {
		return table;
	}

	/**
	 * Sets the table name for the response.
	 * @param table The table name.
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * Returns the layout name for the response.
	 * @return The layout name.
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * Sets the layout name for the response.
	 * @param layout The layout name.
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

	/**
	 * Returns the database name for the response.
	 * @return The database name.
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Sets the database name for the response.
	 * @param database The database name.
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

}
