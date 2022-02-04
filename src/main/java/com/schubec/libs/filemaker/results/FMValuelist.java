package com.schubec.libs.filemaker.results;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMValuelist {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("values")
	private List<FMValuelistValue> values;

	public List<FMValuelistValue> getValues() {
		return values;
	}

	public void setValues(List<FMValuelistValue> values) {
		this.values = values;
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
	
}
