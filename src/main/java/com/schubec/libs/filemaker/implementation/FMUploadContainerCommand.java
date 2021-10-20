package com.schubec.libs.filemaker.implementation;

import java.io.File;
import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;

import com.schubec.libs.filemaker.base.FMCommandWithoutBody;

public class FMUploadContainerCommand extends FMCommandWithoutBody {

	private long recordId;
	private String fieldName;
	private int fieldReptition;

	private File file;
	private String fileName;

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public FMUploadContainerCommand(String layout, long recordId, String fieldName) {
		this(layout, recordId, fieldName, 1);

	}

	public FMUploadContainerCommand(String layout, long recordId, String fieldName, int fieldRepition) {
		super(layout);
		setRecordId(recordId);
		setFieldName(fieldName);
		setFieldReptition(fieldRepition);

	}

	public String getEndpoint() {
		return "/layouts/" + getLayout() + "/records/" + getRecordId() + "/containers/" + getFieldName() + "/" + getFieldReptition();
	}

	@Override
	public HttpEntityEnclosingRequestBase getHttpCommand(URI uri) {
		return new HttpPost(uri);
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getFieldReptition() {
		return fieldReptition;
	}

	public void setFieldReptition(int fieldReptition) {
		this.fieldReptition = fieldReptition;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		setFileName(file.getName());
	}

	public void setFile(File file, String fileName) {
		this.file = file;
		setFileName(fileName);
	}
}
