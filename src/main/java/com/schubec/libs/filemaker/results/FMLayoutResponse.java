package com.schubec.libs.filemaker.results;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FMLayoutResponse {

	@JsonProperty("fieldMetaData")
	private List<FMFieldMetadata> fieldMetaData;

	@JsonIgnore
	@JsonProperty("portalMetaData")
	private String portalMetaData;

	@JsonProperty("valueLists")
	private List<FMValuelist> valueLists;

	public List<FMValuelist> getValueLists() {
		if (valueLists == null) {
			valueLists = Collections.emptyList();
		}
		return valueLists;
	}

	public void setValueLists(List<FMValuelist> valueLists) {
		this.valueLists = valueLists;
	}

	public List<FMFieldMetadata> getFieldMetaData() {
		if (fieldMetaData == null) {
			fieldMetaData = Collections.emptyList();
		}
		return fieldMetaData;
	}

	public void setFieldMetaData(List<FMFieldMetadata> fieldMetaData) {
		this.fieldMetaData = fieldMetaData;
	}

}
