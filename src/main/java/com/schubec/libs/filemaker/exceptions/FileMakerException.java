package com.schubec.libs.filemaker.exceptions;

public class FileMakerException extends Exception {
	public static final int ERRORCODE_OK = 0;
	public static final int ERRORCODE_UNKOWN_ERROR = -100;
	public static final int ERRORCODE_URI_SYNTAX_ERROR = -101;
	public static final int ERRORCODE_IO_ERROR = -102;
	public static final int ERRORCODE_INVALID_HTTP_STATUS = -103;

	private int errorCode = ERRORCODE_OK;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileMakerException(int code, String message) {
		super(message);
		setErrorCode(code);
	}

	public FileMakerException(int code, String message, Throwable cause) {
		super(message, cause);
		setErrorCode(code);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
