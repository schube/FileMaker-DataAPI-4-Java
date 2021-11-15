package com.schubec.libs.filemaker.base;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;

import com.schubec.libs.filemaker.implementation.FMScript;

public abstract class FMCommandBase {
	private String layout;
	private FMScript script;
	private FMScript scriptPreRequest;
	private FMScript scriptPreSort;

	private List<NameValuePair> urlParameters = new LinkedList<>();

	public abstract String getEndpoint();

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public abstract HttpRequestBase getHttpCommand(URI uri);

	public FMScript getScript() {
		return script;
	}

	public void setScript(FMScript script) {
		this.script = script;
	}

	public FMScript getScriptPreRequest() {
		return scriptPreRequest;
	}

	public void setScriptPreRequest(FMScript scriptPreRequest) {
		this.scriptPreRequest = scriptPreRequest;
	}

	public FMScript getScriptPreSort() {
		return scriptPreSort;
	}

	public void setScriptPreSort(FMScript scriptPreSort) {
		this.scriptPreSort = scriptPreSort;
	}

	public abstract void prepareCommand();
	
	public void addUrlParameter(final String name, final String value) {
		urlParameters.add(new BasicNameValuePair(name, value));
	}

	public List<NameValuePair> getUrlParameters() {
		return urlParameters;
	}

}
