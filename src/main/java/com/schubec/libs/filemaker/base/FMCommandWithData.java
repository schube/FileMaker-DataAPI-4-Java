package com.schubec.libs.filemaker.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Abstract base class for FileMaker commands that include a JSON body.
 * Provides methods for building and serializing command data.
 */
public abstract class FMCommandWithData extends FMCommandBase {

	protected final ObjectMapper mapper = new ObjectMapper();

	protected final ObjectNode fmJsonObject;

	/**
	 * Default constructor. Initializes the JSON object for the command.
	 */
	public FMCommandWithData() {
		fmJsonObject = mapper.createObjectNode();
	}

	/**
	 * Constructor with layout name.
	 * @param layout The layout name.
	 */
	public FMCommandWithData(String layout) {
		this();
		setLayout(layout);
	}

	/**
	 * Builds the command's JSON body. Must be implemented by subclasses.
	 */
	protected abstract void buildCommand();

	/**
	 * Prepares the command for execution by building the command body.
	 */
	@Override
	public void prepareCommand() {
		buildCommand();
	}
	
	/**
	 * Returns the command as a Jackson ObjectNode for serialization.
	 * @return The ObjectNode representing the command.
	 */
	public ObjectNode asJsonNode() {
		if (this.getScript() != null) {
			getFmJsonObject().put("script", this.getScript().getScriptName());
			if (this.getScript().getScriptParameter() != null) {
				getFmJsonObject().put("script.param", this.getScript().getScriptParameter());
			}
		}
		if (this.getScriptPreRequest() != null) {
			getFmJsonObject().put("script.prerequest", this.getScriptPreRequest().getScriptName());
			if (this.getScriptPreRequest().getScriptParameter() != null) {
				getFmJsonObject().put("script.prerequest.param", this.getScriptPreRequest().getScriptParameter());
			}
		}
		if (this.getScriptPreSort() != null) {
			getFmJsonObject().put("script.presort", this.getScriptPreSort().getScriptName());
			if (this.getScriptPreSort().getScriptParameter() != null) {
				getFmJsonObject().put("script.presort.param", this.getScriptPreSort().getScriptParameter());
			}
		}
		return getFmJsonObject();
	}

	/**
	 * Returns the internal JSON object for this command.
	 * @return The ObjectNode.
	 */
	public ObjectNode getFmJsonObject() {
		return fmJsonObject;
	}

}
