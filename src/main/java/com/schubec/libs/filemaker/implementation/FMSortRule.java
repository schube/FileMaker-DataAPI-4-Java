package com.schubec.libs.filemaker.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FMSortRule {

	/**
	 * Sort direction constants.
	 */
	public static final String SORT_ASCEND = "ascend";
	public static final String SORT_DESCEND = "descend";

	private String fieldName;
	private String sortorder_or_valuelistname = SORT_ASCEND;

	public FMSortRule() {

	}

	public FMSortRule(String field) {
		this(field, SORT_ASCEND);
	}

	public FMSortRule(String field, String sortorder_or_valuelistname) {
		this.setFieldName(field);
		this.setSortorder(sortorder_or_valuelistname);
	}

	public JsonNode asJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();

		objectNode.put("fieldName", getFieldName());
		objectNode.put("sortOrder", getSortorder());

		return objectNode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getSortorder() {
		return sortorder_or_valuelistname;
	}

	public void setSortorder(String sortorder_or_valuelistname) {
		this.sortorder_or_valuelistname = sortorder_or_valuelistname;
	}

}
