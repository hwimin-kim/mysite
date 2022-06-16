package com.douzone.mysite.exception;

import java.io.IOException;

public class FileUploadException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FileUploadException() {
		super("FileUploadException Occurs...");
	}
	
	public FileUploadException(IOException e) {
		super(e);
	}
}
