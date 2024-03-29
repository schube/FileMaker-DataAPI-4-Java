package com.schubec.libs.filemaker.implementation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

	public FMFindCriterion() {

	}

	public FMFindCriterion(String field, String criterion) {
		this(field, criterion, false);
	}

	public FMFindCriterion(String field, String criterion, boolean omit) {
		addCriterion(field, criterion);
		setOmit(omit);
	}

	public FMFindCriterion(String field, int criterion) {
		this(field, criterion, false);
	}

	public FMFindCriterion(String field, int criterion, boolean b) {
		addCriterion(field, criterion);
		setOmit(omit);
	}

	private FMFindCriterion addCriterion(String field, int criterion) {
		fieldsAndSearchvalues.put(field, criterion);
		return this;

	}

	public FMFindCriterion addCriterion(String field, String criterion) {
		fieldsAndSearchvalues.put(field, criterion);
		return this;
	}

	public boolean isOmit() {
		return omit;
	}

	public FMFindCriterion setOmit(boolean omit) {
		this.omit = omit;
		return this;
	}

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
