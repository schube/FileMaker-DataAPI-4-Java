package com.schubec.libs.filemaker.base;

public abstract class FMCommandWithoutBody extends FMCommandBase {

	//@TODO script als URL Parameter
	
	public FMCommandWithoutBody(String layout) {
		setLayout(layout);
	}

}
