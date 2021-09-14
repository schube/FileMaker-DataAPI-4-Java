package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMScriptsResponse {

	@JsonProperty("scripts")
	private Script[] scripts;

	public Script[] getScripts() {
		return scripts;
	}

	public void setScripts(Script[] data) {
		this.scripts = data;
	}

}
