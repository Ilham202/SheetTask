package com.task.exception;

public class FileFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FileFormatException(String message) {
		super(message,null,false,false);
	}

}
