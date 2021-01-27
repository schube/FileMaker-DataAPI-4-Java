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

	public ObjectNode asJsonNode() {
		buildCommand();
		return getFmJsonObject();
	}

	public ObjectNode getFmJsonObject() {

		return fmJsonObject;
	}

}
