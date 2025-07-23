package com.schubec.libs.filemaker.exceptions;

/**
 * Exception class for FileMaker Data API errors.
 * Contains error codes and messages for various failure scenarios.
 */
public class FileMakerException extends Exception {
	public static final int ERRORCODE_OK = 0;
	public static final int ERRORCODE_UNKOWN_ERROR = -100;
	public static final int ERRORCODE_URI_SYNTAX_ERROR = -101;
	public static final int ERRORCODE_IO_ERROR = -102;
	public static final int ERRORCODE_INVALID_HTTP_STATUS = -103;
	public static final int ERRORCODE_MISSING_VALUE = -104;
	private int errorCode = ERRORCODE_OK;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new FileMakerException with the specified error code and message.
	 * @param code The error code.
	 * @param message The error message.
	 */
	public FileMakerException(int code, String message) {
		super(message);
		setErrorCode(code);
	}

	/**
	 * Constructs a new FileMakerException with the specified error code, message, and cause.
	 * @param code The error code.
	 * @param message The error message.
	 * @param cause The cause of the exception.
	 */
	public FileMakerException(int code, String message, Throwable cause) {
		super(message, cause);
		setErrorCode(code);
	}

	/**
	 * Returns the error code associated with this exception.
	 * @return The error code.
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code for this exception.
	 * @param errorCode The error code.
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
