package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	
	
	public int getRepetitionEnd() {
		return repetitionEnd;
	}

	public void setRepetitionEnd(int repetitionEnd) {
		this.repetitionEnd = repetitionEnd;
	}

	public int getRepetitionStart() {
		return repetitionStart;
	}

	public void setRepetitionStart(int repetitionStart) {
		this.repetitionStart = repetitionStart;
	}

	public boolean isTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(boolean timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public boolean isNumeric() {
		return numeric;
	}

	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}

	public boolean isNotEmpty() {
		return notEmpty;
	}

	public void setNotEmpty(boolean notEmpty) {
		this.notEmpty = notEmpty;
	}

	public long getMaxCharacters() {
		return maxCharacters;
	}

	public void setMaxCharacters(long maxCharacters) {
		this.maxCharacters = maxCharacters;
	}

	public int getMaxRepeat() {
		return maxRepeat;
	}

	public void setMaxRepeat(int maxRepeat) {
		this.maxRepeat = maxRepeat;
	}

	public boolean isFourDigitYear() {
		return fourDigitYear;
	}

	public void setFourDigitYear(boolean fourDigitYear) {
		this.fourDigitYear = fourDigitYear;
	}

	public boolean isAutoEnter() {
		return autoEnter;
	}

	public void setAutoEnter(boolean autoEnter) {
		this.autoEnter = autoEnter;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValueList() {
		return valueList;
	}

	public void setValueList(String valueList) {
		this.valueList = valueList;
	}
}
