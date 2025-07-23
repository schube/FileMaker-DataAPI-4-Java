package com.schubec.libs.filemaker.base;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.schubec.libs.filemaker.implementation.FMScript;

/**
 * Abstract base class for FileMaker Data API commands.
 * Provides common properties and methods for command configuration.
 */
public abstract class FMCommandBase {
	private String layout;
	private FMScript script;
	private FMScript scriptPreRequest;
	private FMScript scriptPreSort;

	private List<NameValuePair> urlParameters = new LinkedList<>();

	/**
	 * Returns the endpoint path for this command.
	 * @return The endpoint path as a String.
	 */
	public abstract String getEndpoint();

	/**
	 * Returns the layout name associated with this command.
	 * @return The layout name.
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * Sets the layout name for this command.
	 * @param layout The layout name.
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}

	/**
	 * Returns the HTTP command object for this command.
	 * @param uri The URI to use for the request.
	 * @return The HTTP request object.
	 */
	public abstract HttpRequestBase getHttpCommand(URI uri);

	/**
	 * Returns the script to be executed after the main request.
	 * @return The script object.
	 */
	public FMScript getScript() {
		return script;
	}

	/**
	 * Sets the script to be executed after the main request.
	 * @param script The script object.
	 */
	public void setScript(FMScript script) {
		this.script = script;
	}

	/**
	 * Returns the script to be executed before the request.
	 * @return The script object.
	 */
	public FMScript getScriptPreRequest() {
		return scriptPreRequest;
	}

	/**
	 * Sets the script to be executed before the request.
	 * @param scriptPreRequest The script object.
	 */
	public void setScriptPreRequest(FMScript scriptPreRequest) {
		this.scriptPreRequest = scriptPreRequest;
	}

	/**
	 * Returns the script to be executed before sorting.
	 * @return The script object.
	 */
	public FMScript getScriptPreSort() {
		return scriptPreSort;
	}

	/**
	 * Sets the script to be executed before sorting.
	 * @param scriptPreSort The script object.
	 */
	public void setScriptPreSort(FMScript scriptPreSort) {
		this.scriptPreSort = scriptPreSort;
	}

	/**
	 * Prepares the command for execution (e.g., builds request body or parameters).
	 */
	public abstract void prepareCommand();
	
	/**
	 * Adds a URL parameter to the command.
	 * @param name The parameter name.
	 * @param value The parameter value.
	 */
	public void addUrlParameter(final String name, final String value) {
		urlParameters.add(new BasicNameValuePair(name, value));
	}

	/**
	 * Returns the list of URL parameters for this command.
	 * @return The list of NameValuePair objects.
	 */
	public List<NameValuePair> getUrlParameters() {
		return urlParameters;
	}

}
