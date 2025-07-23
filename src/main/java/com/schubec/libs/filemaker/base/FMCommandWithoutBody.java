package com.schubec.libs.filemaker.base;

/**
 * Abstract base class for FileMaker commands that do not require a request body.
 * Handles script parameters as URL parameters.
 */
public abstract class FMCommandWithoutBody extends FMCommandBase {

	/**
	 * Constructor with layout name.
	 * @param layout The layout name.
	 */
	public FMCommandWithoutBody(String layout) {
		setLayout(layout);
	}

	/**
	 * Prepares the command by adding script parameters as URL parameters.
	 */
	@Override
	public void prepareCommand() {
		if (this.getScript() != null) {
			addUrlParameter("script", this.getScript().getScriptName());
			if (this.getScript().getScriptParameter() != null) {
				addUrlParameter("script.param", this.getScript().getScriptParameter());
			}
		}
		if (this.getScriptPreRequest() != null) {
			addUrlParameter("script.prerequest", this.getScriptPreRequest().getScriptName());
			if (this.getScriptPreRequest().getScriptParameter() != null) {
				addUrlParameter("script.prerequest.param", this.getScriptPreRequest().getScriptParameter());
			}
		}
		if (this.getScriptPreSort() != null) {
			addUrlParameter("script.presort", this.getScriptPreSort().getScriptName());
			if (this.getScriptPreSort().getScriptParameter() != null) {
				addUrlParameter("script.presort.param", this.getScriptPreSort().getScriptParameter());
			}
		}
	};

}
