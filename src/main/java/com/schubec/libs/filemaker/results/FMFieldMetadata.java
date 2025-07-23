package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Metadata about a FileMaker field, including type, value list, and validation info.
 */
public class FMFieldMetadata {
	@JsonProperty("name")
	private String name;

	@JsonProperty("type")
	private String type;

	@JsonProperty("valueList")
	private String valueList;
	
	@JsonProperty("displayType")
	private String displayType;

	@JsonProperty("result")
	private String result;

	@JsonProperty("global")
	private boolean global;

	@JsonProperty("autoEnter")
	private boolean autoEnter;

	@JsonProperty("fourDigitYear")
	private boolean fourDigitYear;

	@JsonProperty("maxRepeat")
	private int maxRepeat;

	@JsonProperty("maxCharacters")
	private long maxCharacters;

	@JsonProperty("notEmpty")
	private boolean notEmpty;

	@JsonProperty("numeric")
	private boolean numeric;

	@JsonProperty("timeOfDay")
	private boolean timeOfDay;

	@JsonProperty("repetitionStart")
	private int repetitionStart;

	@JsonProperty("repetitionEnd")
	private int repetitionEnd;

	
	
	/**
	 * Returns the repetition end index for this field.
	 * @return The repetition end index.
	 */
	public int getRepetitionEnd() {
		return repetitionEnd;
	}

	/**
	 * Sets the repetition end index for this field.
	 * @param repetitionEnd The repetition end index.
	 */
	public void setRepetitionEnd(int repetitionEnd) {
		this.repetitionEnd = repetitionEnd;
	}

	/**
	 * Returns the repetition start index for this field.
	 * @return The repetition start index.
	 */
	public int getRepetitionStart() {
		return repetitionStart;
	}

	/**
	 * Sets the repetition start index for this field.
	 * @param repetitionStart The repetition start index.
	 */
	public void setRepetitionStart(int repetitionStart) {
		this.repetitionStart = repetitionStart;
	}

	/**
	 * Returns true if this field is a time-of-day field.
	 * @return true if time-of-day, false otherwise.
	 */
	public boolean isTimeOfDay() {
		return timeOfDay;
	}

	/**
	 * Sets whether this field is a time-of-day field.
	 * @param timeOfDay true if time-of-day, false otherwise.
	 */
	public void setTimeOfDay(boolean timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	/**
	 * Returns true if this field is numeric.
	 * @return true if numeric, false otherwise.
	 */
	public boolean isNumeric() {
		return numeric;
	}

	/**
	 * Sets whether this field is numeric.
	 * @param numeric true if numeric, false otherwise.
	 */
	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	/**
	 * Returns true if this field is not empty.
	 * @return true if not empty, false otherwise.
	 */
	public boolean isNotEmpty() {
		return notEmpty;
	}

	/**
	 * Sets whether this field is not empty.
	 * @param notEmpty true if not empty, false otherwise.
	 */
	public void setNotEmpty(boolean notEmpty) {
		this.notEmpty = notEmpty;
	}

	/**
	 * Returns the maximum number of characters allowed for this field.
	 * @return The maximum number of characters.
	 */
	public long getMaxCharacters() {
		return maxCharacters;
	}

	/**
	 * Sets the maximum number of characters allowed for this field.
	 * @param maxCharacters The maximum number of characters.
	 */
	public void setMaxCharacters(long maxCharacters) {
		this.maxCharacters = maxCharacters;
	}

	/**
	 * Returns the maximum number of repetitions for this field.
	 * @return The maximum number of repetitions.
	 */
	public int getMaxRepeat() {
		return maxRepeat;
	}

	/**
	 * Sets the maximum number of repetitions for this field.
	 * @param maxRepeat The maximum number of repetitions.
	 */
	public void setMaxRepeat(int maxRepeat) {
		this.maxRepeat = maxRepeat;
	}

	/**
	 * Returns true if this field uses a four-digit year.
	 * @return true if four-digit year, false otherwise.
	 */
	public boolean isFourDigitYear() {
		return fourDigitYear;
	}

	/**
	 * Sets whether this field uses a four-digit year.
	 * @param fourDigitYear true if four-digit year, false otherwise.
	 */
	public void setFourDigitYear(boolean fourDigitYear) {
		this.fourDigitYear = fourDigitYear;
	}

	/**
	 * Returns true if this field is auto-entered.
	 * @return true if auto-entered, false otherwise.
	 */
	public boolean isAutoEnter() {
		return autoEnter;
	}

	/**
	 * Sets whether this field is auto-entered.
	 * @param autoEnter true if auto-entered, false otherwise.
	 */
	public void setAutoEnter(boolean autoEnter) {
		this.autoEnter = autoEnter;
	}

	/**
	 * Returns true if this field is global.
	 * @return true if global, false otherwise.
	 */
	public boolean isGlobal() {
		return global;
	}

	/**
	 * Sets whether this field is global.
	 * @param global true if global, false otherwise.
	 */
	public void setGlobal(boolean global) {
		this.global = global;
	}

	/**
	 * Returns the result type for this field.
	 * @return The result type.
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result type for this field.
	 * @param result The result type.
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Returns the display type for this field.
	 * @return The display type.
	 */
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * Sets the display type for this field.
	 * @param displayType The display type.
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	/**
	 * Returns the field type (e.g., text, number).
	 * @return The field type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the field type.
	 * @param type The field type.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the field name.
	 * @return The field name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the field name.
	 * @param name The field name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the value list name for this field, if any.
	 * @return The value list name.
	 */
	public String getValueList() {
		return valueList;
	}

	/**
	 * Sets the value list name for this field.
	 * @param valueList The value list name.
	 */
	public void setValueList(String valueList) {
		this.valueList = valueList;
	}
}
