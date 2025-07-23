package com.schubec.libs.filemaker.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents a sort rule for FileMaker queries.
 * Supports ascending and descending order.
 */
public class FMSortRule {

	/**
	 * Sort direction constants.
	 */
	public static final String SORT_ASCEND = "ascend";
	public static final String SORT_DESCEND = "descend";

	private String fieldName;
	private String sortorder_or_valuelistname = SORT_ASCEND;

	/**
	 * Default constructor.
	 */
	public FMSortRule() {

	}

	/**
	 * Constructs a sort rule for a field with ascending order.
	 * @param field The field name.
	 */
	public FMSortRule(String field) {
		this(field, SORT_ASCEND);
	}

	/**
	 * Constructs a sort rule for a field and sort order or value list name.
	 * @param field The field name.
	 * @param sortorder_or_valuelistname The sort order or value list name.
	 */
	public FMSortRule(String field, String sortorder_or_valuelistname) {
		this.setFieldName(field);
		this.setSortorder(sortorder_or_valuelistname);
	}

	/**
	 * Returns this sort rule as a Jackson JsonNode for serialization.
	 * @return The JsonNode representing this sort rule.
	 */
	public JsonNode asJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();

		objectNode.put("fieldName", getFieldName());
		objectNode.put("sortOrder", getSortorder());

		return objectNode;
	}

	/**
	 * Returns the field name for this sort rule.
	 * @return The field name.
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the field name for this sort rule.
	 * @param fieldName The field name.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * Returns the sort order or value list name for this sort rule.
	 * @return The sort order or value list name.
	 */
	public String getSortorder() {
		return sortorder_or_valuelistname;
	}

	/**
	 * Sets the sort order or value list name for this sort rule.
	 * @param sortorder_or_valuelistname The sort order or value list name.
	 */
	public void setSortorder(String sortorder_or_valuelistname) {
		this.sortorder_or_valuelistname = sortorder_or_valuelistname;
	}

}
