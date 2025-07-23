package com.schubec.libs.filemaker.implementation;

/**
 * Represents a FileMaker script to be executed as part of a command.
 * Supports script name and optional parameter.
 */
public class FMScript {

	private String scriptName;
	private String scriptParameter;

	/**
	 * Default constructor.
	 */
	public FMScript() {

	}

	/**
	 * Constructs a script with the given name.
	 * @param scriptName The script name.
	 */
	public FMScript(String scriptName) {
		this(scriptName, null);
	}

	/**
	 * Constructs a script with the given name and parameter.
	 * @param scriptName The script name.
	 * @param scriptParameter The script parameter.
	 */
	public FMScript(String scriptName, String scriptParameter) {
		setScriptName(scriptName);
		setScriptParameter(scriptParameter);
	}

	/**
	 * Returns the script name.
	 * @return The script name.
	 */
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * Sets the script name.
	 * @param scriptName The script name.
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * Returns the script parameter.
	 * @return The script parameter.
	 */
	public String getScriptParameter() {
		return scriptParameter;
	}

	/**
	 * Sets the script parameter.
	 * @param scriptParameter The script parameter.
	 */
	public void setScriptParameter(String scriptParameter) {
		this.scriptParameter = scriptParameter;
	}

}
