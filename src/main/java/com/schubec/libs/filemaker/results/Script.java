package com.schubec.libs.filemaker.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Script {

	@JsonProperty("folderScriptNames")
	private Script[] folderScriptNames;
	
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("isFolder")
	private boolean isFolder;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public Script[] getFolderScriptNames() {
		return folderScriptNames;
	}

	public void setFolderScriptNames(Script[] folderScriptNames) {
		this.folderScriptNames = folderScriptNames;
	}


}
