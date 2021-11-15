package com.schubec.libs.filemaker.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class FMCommandWithData extends FMCommandBase {

	protected final ObjectMapper mapper = new ObjectMapper();

	protected final ObjectNode fmJsonObject;

	public FMCommandWithData() {
		fmJsonObject = mapper.createObjectNode();
	}

	public FMCommandWithData(String layout) {
		this();
		setLayout(layout);
	}

	protected abstract void buildCommand();

	@Override
	public void prepareCommand() {
		buildCommand();
	}
	
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

	public ObjectNode getFmJsonObject() {
		return fmJsonObject;
	}

}
