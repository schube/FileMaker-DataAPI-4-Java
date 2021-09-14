package com.schubec.libs.filemaker.implementation;

public class FMScript {

	private String scriptName;
	private String scriptParameter;

	public FMScript() {

	}

	public FMScript(String scriptName) {
		this(scriptName, null);
	}

	public FMScript(String scriptName, String scriptParameter) {
		setScriptName(scriptName);
		setScriptParameter(scriptParameter);
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public String getScriptParameter() {
		return scriptParameter;
	}

	public void setScriptParameter(String scriptParameter) {
		this.scriptParameter = scriptParameter;
	}

}
