package org.linagora.exception;

public class ExceptionGeneratorActiviti extends Exception {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}
	public ExceptionGeneratorActiviti(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	public ExceptionGeneratorActiviti() {
		super();
	}
}