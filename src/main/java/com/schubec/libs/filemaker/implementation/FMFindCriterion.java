package com.schubec.libs.filemaker.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents a single find criterion for FileMaker find queries.
 * Supports various operators and omit flag.
 */
public class FMFindCriterion {

	/**
	 * Find constants.
	 */
	public static final String FIND_LT = "<";
	public static final String FIND_LTE = "<=";
	public static final String FIND_GT = ">";
	public static final String FIND_GTE = ">=";
	public static final String FIND_RANGE = "...";
	public static final String FIND_DUPLICATES = "!";
	public static final String FIND_TODAY = "//";
	public static final String FIND_INVALID_DATETIME = "?";
	public static final String FIND_CHAR = "@";
	public static final String FIND_DIGIT = "#";
	public static final String FIND_CHAR_WILDCARD = "*";
	public static final String FIND_LITERAL = "\"";
	public static final String FIND_RELAXED = "~";
	public static final String FIND_FIELDMATCH = "==";
	
	
	private Map<String, Object> fieldsAndSearchvalues = new HashMap<>();
	private boolean omit;

	/**
	 * Default constructor.
	 */
	public FMFindCriterion() {

	}

	/**
	 * Constructs a find criterion for a field and string value.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 */
	public FMFindCriterion(String field, String criterion) {
		this(field, criterion, false);
	}

	/**
	 * Constructs a find criterion for a field and string value, with omit flag.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 * @param omit true to omit matching records, false otherwise.
	 */
	public FMFindCriterion(String field, String criterion, boolean omit) {
		addCriterion(field, criterion);
		setOmit(omit);
	}

	/**
	 * Constructs a find criterion for a field and integer value.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 */
	public FMFindCriterion(String field, int criterion) {
		this(field, criterion, false);
	}

	/**
	 * Constructs a find criterion for a field and integer value, with omit flag.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 * @param omit true to omit matching records, false otherwise.
	 */
	public FMFindCriterion(String field, int criterion, boolean omit) {
		addCriterion(field, criterion);
		setOmit(omit);
	}

	/**
	 * Adds a criterion for a field and integer value.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 * @return This FMFindCriterion instance.
	 */
	private FMFindCriterion addCriterion(String field, int criterion) {
		fieldsAndSearchvalues.put(field, criterion);
		return this;

	}

	/**
	 * Adds a criterion for a field and string value.
	 * @param field The field name.
	 * @param criterion The criterion value.
	 * @return This FMFindCriterion instance.
	 */
	public FMFindCriterion addCriterion(String field, String criterion) {
		fieldsAndSearchvalues.put(field, criterion);
		return this;
	}

	/**
	 * Returns whether this criterion omits matching records.
	 * @return true if omit, false otherwise.
	 */
	public boolean isOmit() {
		return omit;
	}

	/**
	 * Sets whether this criterion omits matching records.
	 * @param omit true to omit, false otherwise.
	 * @return This FMFindCriterion instance.
	 */
	public FMFindCriterion setOmit(boolean omit) {
		this.omit = omit;
		return this;
	}

	/**
	 * Returns this criterion as a Jackson JsonNode for serialization.
	 * @return The JsonNode representing this criterion.
	 */
	public JsonNode asJsonNode() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		for (Entry<String, Object> kv : fieldsAndSearchvalues.entrySet()) {
			if (kv.getValue() instanceof Integer) {
				objectNode.put(kv.getKey(), (Integer) kv.getValue());
			} else {
				objectNode.put(kv.getKey(), (String) kv.getValue());
			}

		}

		if (isOmit()) {
			objectNode.put("omit", "true");
		}
		return objectNode;
	}

}
